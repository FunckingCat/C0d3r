package ru.davidzh.coder.backend.dao.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.davidzh.coder.backend.dao.entity.UserEntity
import java.util.*

/**
 * Repository interface for performing CRUD operations on the [UserEntity] class.
 *
 * This interface provides methods for accessing and manipulating user data in the database.
 * It extends [CrudRepository], offering basic CRUD functionalities for the [UserEntity].
 */
@Repository
interface UserRepository: CrudRepository<UserEntity, Long> {

    /**
     * Finds a user by their username.
     *
     * @param userName the username of the user to be retrieved.
     * @return an [Optional] containing the [UserEntity] instance associated with the specified username,
     *         or an empty [Optional] if no user is found.
     */
    fun findByUsername(userName: String): Optional<UserEntity>

    fun findByUserId(userId: UUID): Optional<UserEntity>
}