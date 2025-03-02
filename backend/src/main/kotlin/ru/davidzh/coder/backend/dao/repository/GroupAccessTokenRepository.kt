package ru.davidzh.coder.backend.dao.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.davidzh.coder.backend.dao.entity.GroupAccessTokenEntity
import java.util.*

/**
 * Repository interface for managing [GroupAccessTokenEntity] entities in the database.
 * Provides basic CRUD operations and custom query methods.
 */
@Repository
interface GroupAccessTokenRepository : CrudRepository<GroupAccessTokenEntity, Int> {

    /**
     * Finds all access tokens associated with a specific group.
     *
     * @param groupId the UUID of the group to search for
     * @return a [GroupAccessTokenEntity] matching the given group ID,
     *         or null if none found
     */
    fun findByGroupId(groupId: UUID): GroupAccessTokenEntity?

    /**
     * Finds an access token by its token string.
     *
     * @param accessToken the token string to search for
     * @return the [GroupAccessTokenEntity] matching the token, or null if not found
     */
    fun findByAccessToken(accessToken: String): GroupAccessTokenEntity?
}