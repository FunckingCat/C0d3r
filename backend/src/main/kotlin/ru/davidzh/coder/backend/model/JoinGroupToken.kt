package ru.davidzh.coder.backend.model

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Represents a token generated for a specific group, allowing users to join that group by presenting this token.")
data class JoinGroupToken(

    @field:Schema(
        description = "The unique identifier (UUID) of the group this token is associated with.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "a1b2c3d4-e5f6-7890-1234-567890abcdef"
    )
    val groupId: UUID,

    @field:Schema(
        description = "The actual token string that must be presented by a user when attempting to join the group identified by `groupId`. These tokens are typically time-limited or single-use.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "jgt-a1b2c3d4e5f67890-xyz789" // Example join group token format
    )
    val token: String
)
