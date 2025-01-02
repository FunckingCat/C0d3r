package ru.davidzh.coder.backend.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.davidzh.coder.backend.controller.dto.RegisterUserRequest
import ru.davidzh.coder.backend.service.RegisterUserService
import ru.davidzh.coder.backend.util.extension.asResponseEntity

@RestController
@RequestMapping("/public/api/v1/registration")
class RegistrationController(
    private val registerUserService: RegisterUserService
) {

    @PostMapping
    fun registerUser(@RequestBody request: RegisterUserRequest) =
        registerUserService.registerUser(request).asResponseEntity()

}
