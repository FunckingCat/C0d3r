package ru.davidzh.coder.backend.controller.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request payload required for a user to join an existing group.")
data class JoinGroupRequest(

    @field:Schema(
        description = "The unique access token provided (e.g., via an invitation link or direct sharing) that grants permission to join a specific group. This token is typically short-lived and single-use or time-bound.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "jgt-a1b2c3d4e5f67890"
    )
    val token: String
)

