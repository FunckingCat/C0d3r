package ru.davidzh.coder.backend.controller.dto

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Data class representing a request to reset a user's password.
 *
 * This class contains the necessary information to reset a user's password,
 * including the username and new password. The username is stored in its original
 * form, but a lowercase version is derived for consistency during the password reset process.
 */
@Schema(description = "Request to reset a user's password, containing username and new password.")
data class ResetPasswordRequest(

    @Schema(description = "Имя пользователя для сброса пароля")
    private val username: String,

    @Schema(description = "Новый пароль пользователя")
    val password: String
) {
    val usernameLower: String
        get() = username.lowercase()
}

