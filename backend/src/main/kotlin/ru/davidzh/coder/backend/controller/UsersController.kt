package ru.davidzh.coder.backend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.davidzh.coder.backend.util.extension.asResponseEntity

@RestController
@RequestMapping("/api/v1/users")
class UsersController {

    @GetMapping("/hello")
    fun index() = "Hello!".asResponseEntity()

}
