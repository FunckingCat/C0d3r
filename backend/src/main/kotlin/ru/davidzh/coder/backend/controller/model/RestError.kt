package ru.davidzh.coder.backend.controller.model

import io.swagger.v3.oas.annotations.media.Schema

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
@Schema(description = "Standard error structure returned by the API when a request fails. Contains details about the HTTP status, a summary message, and a more detailed description.")
data class RestError(

    @field:Schema(
        description = "The HTTP status code associated with this error.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "500"
    )
    val status: Int,

    @field:Schema(
        description = "A short, human-readable summary of the error.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "Internal Server Error"
    )
    val message: String,

    @field:Schema(
        description = "A more detailed explanation of the error, potentially including specifics about what went wrong. Can be used for debugging or providing more context to the user.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "An unexpected condition was encountered: Database connection timed out."
    )
    val description: String

)
