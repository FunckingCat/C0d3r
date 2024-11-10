package ru.davidzh.coder.backend.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.davidzh.coder.backend.controller.model.LogInUserRequest
import ru.davidzh.coder.backend.controller.model.ResetPasswordRequest
import ru.davidzh.coder.backend.service.AuthenticationService

@RestController
@RequestMapping("/api/v1/authentication")
class AuthenticationController(
    private val authenticationService: AuthenticationService,
) {

    @PostMapping("/log-in")
    fun logIn(@RequestBody request: LogInUserRequest) = authenticationService.logIn(request)

    @PostMapping("/reset-password")
    fun resetPassword(@RequestBody request: ResetPasswordRequest) = authenticationService.resetPassword(request)

}
