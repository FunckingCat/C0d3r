package ru.davidzh.coder.backend.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.davidzh.coder.backend.aop.annotation.LogExecution
import ru.davidzh.coder.backend.controller.dto.LogInUserRequest
import ru.davidzh.coder.backend.controller.dto.ResetPasswordRequest
import ru.davidzh.coder.backend.controller.model.JwtToken
import ru.davidzh.coder.backend.controller.model.RestError
import ru.davidzh.coder.backend.service.AuthenticationService
import ru.davidzh.coder.backend.util.extension.asResponseEntity

/**
 * REST controller for handling user authentication operations.
 *
 * Provides public endpoints for logging in, resetting passwords, and testing authentication mechanisms.
 *
 * @param authenticationService the service responsible for authentication-related logic.
 */
@CrossOrigin
@RestController
@RequestMapping("/public/api/v1/authentication")
@Tag(name = "Authentication", description = "Public endpoints for user authentication (login, password reset).")
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
    @Operation(
        summary = "User Login",
        description = "Authenticates a user based on provided credentials and returns a JWT token upon success."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Authentication successful, JWT token returned",
                content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = JwtToken::class))]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal server error",
                content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
            )
        ]
    )
    fun logIn(@RequestBody request: LogInUserRequest): ResponseEntity<JwtToken> =
        authenticationService.logIn(request).asResponseEntity()

    /**
     * Resets the user's password based on the provided request.
     *
     * @param request the reset password request containing user identification and new password details.
     * @return a [ResponseEntity] indicating the result of the password reset operation.
     */
    @LogExecution
    @PostMapping("/reset-password")
    @Operation(
        summary = "Reset User Password",
        description = "Initiates the password reset process for a user (details depend on implementation, e.g., requires email and new password)."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Password reset request processed successfully (e.g., password updated or reset email sent)",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal server error",
                content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
            )
        ]
    )
    fun resetPassword(@RequestBody request: ResetPasswordRequest): ResponseEntity<Unit> =
        authenticationService.resetPassword(request).asResponseEntity()

}
