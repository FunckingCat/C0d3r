package ru.davidzh.coder.backend.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.davidzh.coder.backend.controller.dto.CreateJobRequest
import ru.davidzh.coder.backend.service.JobService
import ru.davidzh.coder.backend.util.extension.asResponseEntity

@RestController
@RequestMapping("/api/v1/job")
class JobController(
    private val jobService: JobService
) {

    @PostMapping
    fun createJob(@RequestBody createJobRequest: CreateJobRequest) =
        jobService.createJob(createJobRequest).asResponseEntity()

}