package ru.davidzh.coder.backend.controller.model

/**
 * Represents an error response returned from the REST API.
 *
 * This class encapsulates the error status, message, and a detailed description
 * that can be used to inform the client about the nature of the error.
 *
 * @property status The HTTP status code representing the error (e.g., 400, 404, 500).
 * @property message A brief message explaining the error.
 * @property description A detailed description of the error, providing additional context.
 */
data class RestError(
    val status: Int,
    val message: String,
    val description: String,
)
