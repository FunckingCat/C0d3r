package ru.davidzh.coder.backend.model

import java.util.*

data class GroupMember(
    val id: UUID,
    val username: String,
    val permissions: Set<Permission>,
)
