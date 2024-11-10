package ru.davidzh.coder.backend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public/api/v1/users")
class UsersController {

    @GetMapping("/hello")
    fun index() = "Hello!"

}
