package ru.davidzh.coder.backend.dao.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.util.*

@Table("group_access_tokens")
data class GroupAccessTokenEntity(
    /**
     *
     */
    @Id
    val id: Int? = null,

    /**
     *
     */
    val groupId: UUID,

    /**
     *
     */
    val accessToken: String,

    /**
     *
     */
    val createdAt: OffsetDateTime = OffsetDateTime.now()
)