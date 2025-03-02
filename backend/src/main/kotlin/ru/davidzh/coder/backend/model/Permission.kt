package ru.davidzh.coder.backend.model

/**
 * Enum class representing user permission in groups mechanism within the system.
 *
 * This class defines different permissions that a user can have, determining the level of access and
 * permissions granted to the user. Each permission is associated with a specific value.
 *
 * @property value the string value representing the role, used for comparison and identification.
 */
enum class Permission(
    val value: String
) {
    VIEW("VIEW"),
    EDIT("EDIT"),
    RUN("RUN"),
    ADMIN("ADMIN")
}