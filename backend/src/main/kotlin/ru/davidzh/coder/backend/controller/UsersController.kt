package ru.davidzh.coder.backend.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.davidzh.coder.backend.aop.annotation.LogExecution
import ru.davidzh.coder.backend.util.extension.asResponseEntity
import ru.davidzh.coder.backend.util.extension.getUserAuthentication

/**
 * REST controller for user-related operations.
 *
 * Provides endpoints for managing user interactions within the application.
 */
@RestController
@RequestMapping("/api/v1/users")
class UsersController {

    @LogExecution
    @GetMapping("/hello")
    fun index(): ResponseEntity<Any> {
        val user = getUserAuthentication()
        log.info("$user")
        return "Hello! $user".asResponseEntity()
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(UsersController::class.java)
    }

}
