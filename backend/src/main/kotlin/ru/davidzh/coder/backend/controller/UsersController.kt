package ru.davidzh.coder.backend.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.davidzh.coder.backend.aop.annotation.LogExecution
import ru.davidzh.coder.backend.service.UserService
import ru.davidzh.coder.backend.util.extension.asResponseEntity

/**
 * REST controller for user-related operations.
 *
 * Provides endpoints for managing user interactions within the application.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
class UsersController(
    private val userService: UserService
) {

    @LogExecution
    @GetMapping("/user")
    fun index(): ResponseEntity<Any> = userService.getCurrentUser().asResponseEntity()

    companion object {
        val log: Logger = LoggerFactory.getLogger(UsersController::class.java)
    }

}
