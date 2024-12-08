package ru.davidzh.coder.backend.service

import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.davidzh.coder.backend.controller.model.JwtToken
import ru.davidzh.coder.backend.controller.model.LogInUserRequest
import ru.davidzh.coder.backend.controller.model.ResetPasswordRequest
import ru.davidzh.coder.backend.dao.repository.UserRepository

@Service
class AuthenticationService(
    private val keycloakService: KeycloakService,
    private val userRepository: UserRepository
) {

    private val logger = LoggerFactory.getLogger(AuthenticationService::class.java)

    fun logIn(request: LogInUserRequest): JwtToken = keycloakService
        .getUserToken(request.usernameLower, request.password)

    fun resetPassword(request: ResetPasswordRequest) {
        logger.info("Resetting password ${request.usernameLower}")

        val userEntity = userRepository.findByUsername(request.usernameLower)

        if (userEntity.isPresent) {
            keycloakService.resetPassword(userEntity.get().userId, request.password)
        } else {
            throw UsernameNotFoundException("User ${request.usernameLower} not found")
        }
    }

}