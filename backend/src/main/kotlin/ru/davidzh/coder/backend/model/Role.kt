package ru.davidzh.coder.backend.model

/**
 * Enum class representing user roles within the system.
 *
 * This class defines different roles that a user can have, determining the level of access and
 * permissions granted to the user. Each role is associated with a specific value.
 *
 * @property value the string value representing the role, used for comparison and identification.
 */
enum class Role(
    val value: String
) {
    USER("USER"),
    ADMIN("ADMIN")
}