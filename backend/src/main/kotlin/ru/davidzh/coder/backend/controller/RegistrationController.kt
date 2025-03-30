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
import ru.davidzh.coder.backend.controller.dto.RegisterUserRequest
import ru.davidzh.coder.backend.controller.model.JwtToken
import ru.davidzh.coder.backend.controller.model.RestError
import ru.davidzh.coder.backend.service.RegisterUserService
import ru.davidzh.coder.backend.util.extension.asResponseEntity

/**
 * REST controller for handling user registration.
 *
 * Provides an endpoint for users to register in the system.
 *
 * @param registerUserService the service responsible for processing user registration requests.
 */
@CrossOrigin
@RestController
@RequestMapping("/public/api/v1/registration")
@Tag(name = "User Registration", description = "Endpoint for new users to register in the system.")
class RegistrationController(
    private val registerUserService: RegisterUserService
) {

    /**
     * Registers a new user in the system.
     *
     * @param request the registration request containing the user's details.
     * @return a [ResponseEntity] with the result of the registration operation.
     */
    @LogExecution
    @PostMapping
    @Operation(
        summary = "Register New User",
        description = "Creates a new user account based on the provided registration details and returns an authentication token upon success."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201", description = "User registered successfully, JWT token returned", // 201 Created is appropriate
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = JwtToken::class))]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error during registration",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun registerUser(@RequestBody request: RegisterUserRequest): ResponseEntity<JwtToken> =
        registerUserService.registerUser(request).asResponseEntity()

}
