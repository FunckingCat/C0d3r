package ru.davidzh.coder.backend.controller.model

data class JwtToken(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long
)
