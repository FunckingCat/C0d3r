package ru.davidzh.coder.backend.model

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Represents a member within a specific group, detailing their identity and assigned permissions within that group context.")
data class GroupMember(

    @field:Schema(
        description = "The unique identifier (UUID) of the user who is the member.",
        example = "123e4567-e89b-12d3-a456-426614174000"
    )
    val id: UUID,

    @field:Schema(
        description = "The username of the group member.",
        example = "j.doe"
    )
    val username: String,

    @field:ArraySchema(
        schema = Schema(
            description = "A specific permission granted to this member within the context of this group.",
            implementation = Permission::class
        ),
        uniqueItems = true
    )
    @field:Schema(
        description = "The set of permissions this member holds specifically within this group.",
    )
    val permissions: Set<Permission>
)
