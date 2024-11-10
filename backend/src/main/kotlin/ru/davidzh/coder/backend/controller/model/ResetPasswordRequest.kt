package ru.davidzh.coder.backend.controller.model

data class ResetPasswordRequest(
    val username: String,
    val password: String
)
