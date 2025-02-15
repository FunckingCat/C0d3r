package ru.davidzh.coder.backend.service

import org.springframework.stereotype.Service
import ru.davidzh.coder.backend.dao.repository.UserRepository
import ru.davidzh.coder.backend.model.User
import ru.davidzh.coder.backend.util.extension.getUserAuthentication

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun getCurrentUser(): User = getUserAuthentication()
        .let { it to userRepository.findByUserId(it.userId) }
        .let {
            User(
                id = it.first.userId,
                username = it.second.map { entity -> entity.username }.orElse("Anonimus"),
                roles = it.first.roles.toList()
            )
        }

}