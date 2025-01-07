package ru.davidzh.coder.backend.controller

import org.springframework.web.bind.annotation.*
import ru.davidzh.coder.backend.aop.annotation.LogExecution
import ru.davidzh.coder.backend.controller.dto.CreateJobRequest
import ru.davidzh.coder.backend.service.JobService
import ru.davidzh.coder.backend.util.extension.asResponseEntity

@RestController
@RequestMapping("/api/v1/job")
class JobController(
    private val jobService: JobService
) {

    @LogExecution
    @PostMapping
    fun createJob(@RequestBody createJobRequest: CreateJobRequest) =
        jobService.createJob(createJobRequest).asResponseEntity()

    @LogExecution
    @GetMapping
    fun getJobs() = jobService.getJobs().asResponseEntity()

    @LogExecution
    @GetMapping("/{id}")
    fun getJob(@PathVariable id: Long) = jobService.getJob(id).asResponseEntity()

    @LogExecution
    @PostMapping("/cancel/{id}")
    fun cancelJob(@PathVariable id: Long) = jobService.cancelJob(id).asResponseEntity()

    @LogExecution
    @PostMapping("/rerun/{id}")
    fun rerunJob(@PathVariable id: Long) = jobService.rerunJob(id).asResponseEntity()

    @LogExecution
    @PostMapping("/delete/{id}")
    fun deleteJob(@PathVariable id: Long) = jobService.deleteJob(id).asResponseEntity()

    @LogExecution
    @PostMapping("/webhook/run/{id}")
    fun startWebhook(@PathVariable id: Long) = jobService.startWebHookJob(id).asResponseEntity()

}