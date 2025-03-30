package ru.davidzh.coder.backend.model

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Enum class representing user permission in groups mechanism within the system.
 *
 * This class defines different permissions that a user can have, determining the level of access and
 * permissions granted to the user. Each permission is associated with a specific value.
 *
 * @property value the string value representing the role, used for comparison and identification.
 */
@Schema(
    description = """
        Defines the different levels of permissions a user can have within a group context.
        These permissions control the actions a user is allowed to perform on resources
        associated with the group (e.g., jobs, group settings).
    """,
    enumAsRef = true // Helps some tools represent enums better, lists possible values
)
enum class Permission(
    val value: String
) {
    @Schema(description = "Allows viewing resources (e.g., job details, group members) but not modifying them.")
    VIEW("VIEW"),

    @Schema(description = "Allows viewing and modifying resources (e.g., editing job configurations). May imply VIEW.")
    EDIT("EDIT"),

    @Schema(description = "Allows triggering actions on resources (e.g., running or rerunning jobs). May imply VIEW.")
    RUN("RUN"),

    @Schema(description = "Grants full administrative control over resources and group settings (e.g., managing members, changing permissions, deleting resources). Typically implies all other permissions.")
    ADMIN("ADMIN")
}