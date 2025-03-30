package ru.davidzh.coder.backend.model

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Represents a user account within the system, including their identity, roles, and group memberships.")
data class User(

    @field:Schema(
        description = "The unique identifier (UUID) of the user.",
        example = "123e4567-e89b-12d3-a456-426614174000"
    )
    val id: UUID,

    @field:Schema(
        description = "The user's unique username, often used for login.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "jdoe"
    )
    val username: String,


    @field:ArraySchema(
        schema = Schema(
            description = "A role assigned to the user.",
            implementation = Role::class
        )
    )
    @field:Schema(
        description = "A list of roles assigned to the user, determining their permissions.",
    )
    val roles: List<Role>,

    @field:ArraySchema(
        schema = Schema(
            description = "A group the user belongs to.",
            implementation = Group::class
        ),
        uniqueItems = true
    )
    @field:Schema(
        description = "A set of groups the user is a member of.",
    )
    val groups: Set<Group> = emptySet()
)
