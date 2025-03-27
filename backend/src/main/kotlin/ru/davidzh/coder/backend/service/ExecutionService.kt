package ru.davidzh.coder.backend.service

import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.davidzh.coder.backend.converter.JobConverter
import ru.davidzh.coder.backend.dao.entity.ExecutionResultEntity
import ru.davidzh.coder.backend.dao.repository.ExecutionResultRepository
import ru.davidzh.coder.backend.dao.repository.JobRepository
import ru.davidzh.coder.backend.model.*
import ru.davidzh.coder.backend.util.JobNameUtil.containerName
import java.time.LocalDateTime

@Service
class ExecutionService(
    private val kubernetesService: KubernetesService,
    private val jobRepository: JobRepository,
    private val executionResultRepository: ExecutionResultRepository,
    private val jobConverter: JobConverter
) {

//    @EventListener(ApplicationReadyEvent::class)
    @Scheduled(fixedDelay = 5000, initialDelay = 2000)
    fun executeTask() {
        val runningJobs = jobRepository.findByStatus(JobStatus.RUNNING)

        runningJobs
            .filter { it.executionType == ExecutionType.ON_DEMAND }
            .map { jobConverter.convert(it) }
            .forEach { precessOnDemandJobExecution(it) }

        runningJobs
            .filter { it.executionType == ExecutionType.SCHEDULED }
            .map { jobConverter.convert(it) }
            .map { it to kubernetesService.getCronJobExecutionResult(containerName(it.userId, it.name, it.ordinal!!)) }
            .forEach {
                it.second.forEach {
                    intermediateJobState -> precessJobExecution(it.first, intermediateJobState)
                }
            }

        runningJobs
            .filter { it.executionType == ExecutionType.WEBHOOK }
            .map { jobConverter.convert(it) }
            .forEach { precessWebHookJobExecution(it) }

    }

    private fun precessOnDemandJobExecution(job: Job) {
        val intermediateResult: IntermediateJobState = kubernetesService
            .getJobExecutionResult(containerName(job.userId, job.name, job.ordinal!!))
        precessJobExecution(job, intermediateResult)
        if (intermediateResult.status == ExecutionStatus.COMPLETED) {
            jobRepository.findByIdOrNull(job.id!!)!!
                .let { jobRepository.save(it.copy(status = JobStatus.COMPLETED)) }
        }
        if (intermediateResult.status == ExecutionStatus.FAILED) {
            jobRepository.findByIdOrNull(job.id!!)!!
                .let { jobRepository.save(it.copy(status = JobStatus.FAILED)) }
        }
    }

    private fun precessWebHookJobExecution(job: Job) {
        val intermediateResult: IntermediateJobState = kubernetesService
            .getJobExecutionResult(containerName(job.userId, job.name, job.ordinal!!))
        precessJobExecution(job, intermediateResult)
        if (intermediateResult.status == ExecutionStatus.COMPLETED) {
            jobRepository.findByIdOrNull(job.id!!)!!
                .let { jobRepository.save(it.copy(status = JobStatus.PENDING)) }
        }
        if (intermediateResult.status == ExecutionStatus.FAILED) {
            jobRepository.findByIdOrNull(job.id!!)!!
                .let { jobRepository.save(it.copy(status = JobStatus.FAILED)) }
        }
    }

    private fun precessJobExecution(job: Job, intermediateResult: IntermediateJobState) {

        val execution: ExecutionResultEntity? = executionResultRepository
            .findByOriginalJobName(intermediateResult.originalJobName)

        if (execution == null) {
            val newExecutionEntity = ExecutionResultEntity(
                originalJobName = intermediateResult.originalJobName,
                jobId = job.id!!,
                startedAt = LocalDateTime.now(),
                status = intermediateResult.containerStates[0].status,
                recordedAt = intermediateResult.containerStates[0].checkTime,
                logs = intermediateResult.containerStates[0].logs
            )
            executionResultRepository.save(newExecutionEntity)
        } else {
            val updatedExecutionResult: ExecutionResultEntity = execution
                .copy(
                    status = intermediateResult.status,
                    finishedAt = if (terminalStatus(intermediateResult.status)) LocalDateTime.now() else null,
                    recordedAt = intermediateResult.containerStates[0].checkTime,
                    logs = intermediateResult.containerStates[0].logs
                )
            executionResultRepository.save(updatedExecutionResult)
        }

    }

    private fun terminalStatus(status: ExecutionStatus) = setOf(
        ExecutionStatus.COMPLETED, JobStatus.FAILED, ExecutionStatus.CANCELLED
    ).contains(status)

    companion object {
        val log = LoggerFactory.getLogger(ExecutionService::class.java)!!
    }

}