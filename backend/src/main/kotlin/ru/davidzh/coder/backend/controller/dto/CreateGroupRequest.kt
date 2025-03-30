package ru.davidzh.coder.backend.controller.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request payload containing the necessary information to create a new user group.")
data class CreateGroupRequest(

    @field:Schema(
        description = "The desired name for the new group. This name is often used for display purposes and identification.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "Project Alpha Team"
    )
    val name: String
)

