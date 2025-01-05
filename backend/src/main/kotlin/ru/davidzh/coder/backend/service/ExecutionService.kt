package ru.davidzh.coder.backend.service

import jakarta.validation.ValidationException
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.davidzh.coder.backend.converter.ExecutionResultConverter
import ru.davidzh.coder.backend.converter.ExecutionResultEntityConverter
import ru.davidzh.coder.backend.dao.entity.ExecutionResultEntity
import ru.davidzh.coder.backend.dao.repository.ExecutionResultRepository
import ru.davidzh.coder.backend.dao.repository.JobRepository
import ru.davidzh.coder.backend.model.ExecutionResult
import ru.davidzh.coder.backend.model.ExecutionStatus
import ru.davidzh.coder.backend.model.IntermediateJobState

@Service
class ExecutionService(
    private val kubernetesService: KubernetesService,
    private val jobRepository: JobRepository,
    private val executionResultConverter: ExecutionResultConverter,
    private val executionResultEntityConverter: ExecutionResultEntityConverter,
    private val executionResultRepository: ExecutionResultRepository
) {

//    @EventListener(ApplicationReadyEvent::class)
    @Scheduled(fixedDelay = 5000, initialDelay = 2000)
    fun executeTask() {
        val pendingExecutions = executionResultRepository.findByStatus(ExecutionStatus.RUNNING)

        pendingExecutions
            .map { executionResultConverter.convert(it) }
            .forEach { precessExecution(it) }

    }

    private fun precessExecution(execution: ExecutionResult) {
        val job = jobRepository.findByIdOrNull(execution.jobId)
            ?: throw ValidationException("Job ${execution.jobId} not found")
        val intermediateResult: IntermediateJobState = kubernetesService.getExecutionResult(job.userId, job.name)
        log.debug(intermediateResult.toString())
        val updatedExecutionResult: ExecutionResultEntity = executionResultEntityConverter.convert(execution)
            .copy(
                status = intermediateResult.status,
                recordedAt = intermediateResult.checkTime,
                logs = intermediateResult.logs
            )
        executionResultRepository.save(updatedExecutionResult)
    }

    companion object {
        val log = LoggerFactory.getLogger(ExecutionService::class.java)!!
    }

}