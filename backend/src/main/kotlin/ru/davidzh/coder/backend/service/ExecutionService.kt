package ru.davidzh.coder.backend.service

import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.davidzh.coder.backend.converter.JobConverter
import ru.davidzh.coder.backend.converter.JobParametersConverter
import ru.davidzh.coder.backend.dao.entity.ExecutionResultEntity
import ru.davidzh.coder.backend.dao.repository.ExecutionResultRepository
import ru.davidzh.coder.backend.dao.repository.JobRepository
import ru.davidzh.coder.backend.model.*
import ru.davidzh.coder.backend.util.JobNameUtil.containerName
import ru.davidzh.coder.backend.util.extension.getUserAuthentication
import java.time.LocalDateTime

@Service
class ExecutionService(
    private val kubernetesService: KubernetesService,
    private val jobRepository: JobRepository,
    private val executionResultRepository: ExecutionResultRepository,
    private val jobConverter: JobConverter,
    private val jobParametersConverter: JobParametersConverter
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
        val intermediateResult: IntermediateJobState = kubernetesService.getJobExecutionResult(containerName(job.userId, job.name, job.ordinal!!))
        precessJobExecution(job, intermediateResult)
        if (intermediateResult.status == ExecutionStatus.COMPLETED) {
            jobRepository.findByIdOrNull(job.id!!)!!
                .let { jobRepository.save(it.copy(status = JobStatus.COMPLETED)) }
        }
    }

    private fun precessWebHookJobExecution(job: Job) {
        val intermediateResult: IntermediateJobState = kubernetesService.getJobExecutionResult(containerName(job.userId, job.name, job.ordinal!!))
        precessJobExecution(job, intermediateResult)
        if (intermediateResult.status == ExecutionStatus.COMPLETED) {
            jobRepository.findByIdOrNull(job.id!!)!!
                .let { jobRepository.save(it.copy(status = JobStatus.PENDING)) }
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
                status = intermediateResult.status,
                recordedAt = intermediateResult.containerStates[0].checkTime,
                logs = intermediateResult.containerStates[0].logs
            )
            executionResultRepository.save(newExecutionEntity)
        } else {
            val updatedExecutionResult: ExecutionResultEntity = execution
                .copy(
                    status = intermediateResult.status,
                    recordedAt = intermediateResult.containerStates[0].checkTime,
                    logs = intermediateResult.containerStates[0].logs
                )
            executionResultRepository.save(updatedExecutionResult)
        }

    }

    fun startWebHookJob(jobId: Long) {
        val job = (jobRepository.findByIdOrNull(jobId) ?: throw IllegalStateException("Job with ID $jobId not found"))
            .apply { ordinal = ordinal?.plus(1) }

        if (job.userId != getUserAuthentication().userId) throw IllegalStateException("Access denied")

        if (job.status != JobStatus.PENDING) throw IllegalStateException("Job with ID $jobId is not pending")

        val parameters = jobParametersConverter.convert(job)

        kubernetesService.startJob(parameters)

        jobRepository.save(job.copy(status = JobStatus.RUNNING))
    }

    companion object {
        val log = LoggerFactory.getLogger(ExecutionService::class.java)!!
    }

}