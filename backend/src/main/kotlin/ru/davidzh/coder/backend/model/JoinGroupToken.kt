package ru.davidzh.coder.backend.model

import java.util.*

data class JoinGroupToken(
    val groupId: UUID,
    val token: String
)
