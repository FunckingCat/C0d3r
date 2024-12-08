package ru.davidzh.coder.backend.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.davidzh.coder.backend.service.JobService

@RestController
@RequestMapping("/api/v1/job")
class JobController(
    private val jobService: JobService
) {

}