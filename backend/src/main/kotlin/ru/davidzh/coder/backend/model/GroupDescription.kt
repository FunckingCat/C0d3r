package ru.davidzh.coder.backend.model

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Provides detailed information about a specific group, including its identifier, name, and current members.")
data class GroupDescription(

    @field:Schema(
        description = "The unique identifier (UUID) of the group.",
        example = "a1b2c3d4-e5f6-7890-1234-567890abcdef"
    )
    val id: UUID,

    @field:Schema(
        description = "The display name of the group.",
        example = "Administrators"
    )
    val name: String,

    @field:ArraySchema(
        schema = Schema(
            description = "A member belonging to this group.",
            implementation = GroupMember::class
        ),
        uniqueItems = true
    )
    @field:Schema(
        description = "The set of members currently belonging to this group.",
    )
    val members: Set<GroupMember>
)
