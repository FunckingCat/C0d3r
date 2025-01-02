package ru.davidzh.coder.backend.service

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import ru.davidzh.coder.backend.controller.dto.CreateJobRequest
import ru.davidzh.coder.backend.converter.JobConverter
import ru.davidzh.coder.backend.model.ExecutionType
import ru.davidzh.coder.backend.model.Job

@Service
class JobService(
    private val jobConverter: JobConverter,
    private val kubernetesService: KubernetesService
) {

    @EventListener(ApplicationReadyEvent::class)
    fun applicationReady() {
        createJob(
            CreateJobRequest(
                name = "test-job",
                dockerImage = "busybox",
                command = listOf("echo", "Hello from Kubernetes Job!"),
                environmentVariables = emptyMap(),
                executionType = ExecutionType.ON_DEMAND
            )
        )
    }

    fun createJob(job: CreateJobRequest): Job = jobConverter.convert(job)
        .also { kubernetesService.startJob(it) }

}