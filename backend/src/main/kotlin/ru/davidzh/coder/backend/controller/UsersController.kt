package ru.davidzh.coder.backend.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/private/api/v1/users")
class UsersController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello")
    fun index() = "Hello!"

}
