package ru.davidzh.coder.backend.controller.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema

/**
 * Data class representing a request to log in a user.
 *
 * This class contains the necessary information to authenticate a user,
 * including the username and password. The username is stored in its original
 * form, but a lowercase version is derived for consistency during authentication.
 */
@Schema(description = "Request to log in a user, containing username and password.")
data class LogInUserRequest(

    @Schema(description = "Имя пользователя для входа в систему")
    private val username: String,

    @Schema(description = "Пароль для входа в систему")
    val password: String
) {
    @get:JsonIgnore
    val usernameLower: String
        get() = username.lowercase()
}

