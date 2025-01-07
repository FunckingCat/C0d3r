package ru.davidzh.coder.backend.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.davidzh.coder.backend.aop.annotation.LogExecution
import ru.davidzh.coder.backend.service.ExecutionService
import ru.davidzh.coder.backend.util.extension.asResponseEntity

@RestController
@RequestMapping("/api/v1/execution")
class ExecutionController(
    private val executionService: ExecutionService,
) {

    @LogExecution
    @PostMapping("/webhook/{id}")
    fun startWebhook(@PathVariable id: Long) = executionService.startWebHookJob(id).asResponseEntity()

}