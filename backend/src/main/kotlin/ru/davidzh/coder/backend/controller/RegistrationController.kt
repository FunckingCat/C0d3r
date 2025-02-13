package ru.davidzh.coder.backend.controller

import org.springframework.web.bind.annotation.*
import ru.davidzh.coder.backend.aop.annotation.LogExecution
import ru.davidzh.coder.backend.controller.dto.RegisterUserRequest
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
    fun registerUser(@RequestBody request: RegisterUserRequest) =
        registerUserService.registerUser(request).asResponseEntity()

}
