package ru.davidzh.coder.backend.dao.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.davidzh.coder.backend.dao.model.UserEntity
import java.util.*

@Repository
interface UserRepository: CrudRepository<UserEntity, UUID> {

    fun findByUsername(userName: String): Optional<UserEntity>

}