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

/**
 * REST controller for handling user authentication operations.
 *
 * Provides public endpoints for logging in, resetting passwords, and testing authentication mechanisms.
 *
 * @param authenticationService the service responsible for authentication-related logic.
 */
@RestController
@RequestMapping("/public/api/v1/authentication")
class AuthenticationController(
    private val authenticationService: AuthenticationService,
) {

    /**
     * Logs in a user by validating their credentials and generating an authentication token.
     *
     * @param request the login request containing the user's credentials.
     * @return a [ResponseEntity] containing the authentication response.
     */
    @LogExecution
    @PostMapping("/log-in")
    fun logIn(@RequestBody request: LogInUserRequest) =
        authenticationService.logIn(request).asResponseEntity()

    /**
     * Resets the user's password based on the provided request.
     *
     * @param request the reset password request containing user identification and new password details.
     * @return a [ResponseEntity] indicating the result of the password reset operation.
     */
    @LogExecution
    @PostMapping("/reset-password")
    fun resetPassword(@RequestBody request: ResetPasswordRequest) =
        authenticationService.resetPassword(request).asResponseEntity()

    /**
     * Tests the authentication mechanism by retrieving and logging the current user's authentication details.
     *
     * @return a [ResponseEntity] containing the user's authentication details.
     */
    @LogExecution
    @PostMapping("/test")
    fun test(): ResponseEntity<Any> {
        val user = getUserAuthentication()
        log.info("$user")
        return user.asResponseEntity()
    }

}
