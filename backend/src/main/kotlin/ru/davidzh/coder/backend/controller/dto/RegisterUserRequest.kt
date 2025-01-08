package ru.davidzh.coder.backend.controller.dto

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Data class representing a request to register a user.
 *
 * This class contains the necessary information for user registration,
 * including the username and password. The username is stored in its original
 * form, but a lowercase version is derived for consistency during user registration.
 */
@Schema(description = "Request to register a user, containing username and password.")
data class RegisterUserRequest(

    @Schema(description = "Имя пользователя для регистрации")
    private val username: String,

    @Schema(description = "Пароль для регистрации")
    val password: String
) {
    val usernameLower: String
        get() = username.lowercase()
}
