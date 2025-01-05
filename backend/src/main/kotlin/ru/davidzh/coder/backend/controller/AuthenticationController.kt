package ru.davidzh.coder.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.davidzh.coder.backend.aop.annotation.LogExecution
import ru.davidzh.coder.backend.controller.UsersController.Companion.log
import ru.davidzh.coder.backend.controller.dto.LogInUserRequest
import ru.davidzh.coder.backend.controller.dto.ResetPasswordRequest
import ru.davidzh.coder.backend.service.AuthenticationService
import ru.davidzh.coder.backend.util.extension.asResponseEntity
import ru.davidzh.coder.backend.util.extension.getUserAuthentication

@RestController
@RequestMapping("/public/api/v1/authentication")
class AuthenticationController(
    private val authenticationService: AuthenticationService,
) {

    @LogExecution
    @PostMapping("/log-in")
    fun logIn(@RequestBody request: LogInUserRequest) =
        authenticationService.logIn(request).asResponseEntity()

    @LogExecution
    @PostMapping("/reset-password")
    fun resetPassword(@RequestBody request: ResetPasswordRequest) =
        authenticationService.resetPassword(request).asResponseEntity()

    @LogExecution
    @PostMapping("/test")
    fun test(): ResponseEntity<Any> {
        val user = getUserAuthentication()
        log.info("$user")
        return user.asResponseEntity()
    }

}
