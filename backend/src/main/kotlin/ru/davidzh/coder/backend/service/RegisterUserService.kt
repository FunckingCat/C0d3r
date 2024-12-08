package ru.davidzh.coder.backend.service

import org.springframework.stereotype.Service
import ru.davidzh.coder.backend.controller.model.JwtToken
import ru.davidzh.coder.backend.controller.model.RegisterUserRequest
import ru.davidzh.coder.backend.dao.model.UserEntity
import ru.davidzh.coder.backend.dao.repository.UserRepository

@Service
class RegisterUserService(
    private val keycloakService: KeycloakService,
    private val userRepository: UserRepository
) {

    fun registerUser(request: RegisterUserRequest): JwtToken {
        val keycloakUserId = keycloakService.registerUser(request)

        val userEntity = UserEntity(userId = keycloakUserId, username = request.usernameLower)
        userRepository.save(userEntity)

        return keycloakService.getUserToken(request.usernameLower, request.password)
    }

}