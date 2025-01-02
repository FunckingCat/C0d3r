package ru.davidzh.coder.backend.controller.dto

data class RegisterUserRequest(
    private val username: String,
    val password: String
) {
    val usernameLower: String
        get() = username.lowercase()
}
