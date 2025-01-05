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

    @GetMapping
    fun getJobs() = jobService.getJobs().asResponseEntity()

    @GetMapping("/{id}")
    fun getJob(@PathVariable id: String) = jobService.getJob().asResponseEntity()

}