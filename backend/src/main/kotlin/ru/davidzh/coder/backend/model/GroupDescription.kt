package ru.davidzh.coder.backend.model

import java.util.*

data class GroupDescription(
    val id: UUID,
    val name: String,
    val members: Set<GroupMember>,
)
