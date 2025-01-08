package ru.davidzh.coder.backend.dao.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

/**
 * Entity class representing a user in the system.
 *
 * This class stores the details of a user, including their unique identifier, username, and associated user ID.
 */
@Table("users")
data class UserEntity(
    /**
     * Unique identifier of the user entity in the database.
     */
    @Id val id: Long? = null,

    /**
     * Unique user identifier (UUID) for the user.
     */
    val userId: UUID,

    /**
     * Username of the user.
     */
    val username: String
)