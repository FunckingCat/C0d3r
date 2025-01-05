package ru.davidzh.coder.backend.dao.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("users")
data class UserEntity(
    @Id val id: Long? = null,
    val userId: UUID,
    val username: String
) {
}