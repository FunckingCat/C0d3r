package ru.davidzh.coder.backend.controller.model

data class RestError(
    val status: Int,
    val message: String,
    val description: String,
)
