package ru.davidzh.coder.backend.model

import java.util.*

data class Group(
    val name: String,
    val id: UUID,
    val permissions: Set<Permission>,
)
