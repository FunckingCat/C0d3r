package ru.davidzh.coder.backend.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Request payload required to remove (exclude) a member from a specific group. This action typically requires administrative privileges within the group.")
data class ExcludeRequest(

    @field:Schema(
        description = "The unique identifier (UUID) of the user (member) to be excluded from the group.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "123e4567-e89b-12d3-a456-426614174000" // Standard UUID example
    )
    val memberId: UUID,

    @field:Schema(
        description = "The unique identifier (UUID) of the group from which the member should be excluded.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "a1b2c3d4-e5f6-7890-1234-567890abcdef" // Another UUID example
    )
    val groupId: UUID
)

