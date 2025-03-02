package ru.davidzh.coder.backend.model

import java.util.*

data class User(
    val id: UUID,
    val username: String,
    val roles: List<Role>,
    val groups: Set<Group> = emptySet()
)
