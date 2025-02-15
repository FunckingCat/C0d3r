package ru.davidzh.coder.backend.service

import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.CreatedResponseUtil
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.davidzh.coder.backend.configuration.properties.KeycloakConfigurationProperties
import ru.davidzh.coder.backend.controller.dto.RegisterUserRequest
import ru.davidzh.coder.backend.controller.model.JwtToken
import java.util.*

@Service
class KeycloakService(
    private val keycloak: Keycloak,
    private val keycloakConfigurationProperties: KeycloakConfigurationProperties,
) {

    private val logger = LoggerFactory.getLogger(KeycloakService::class.java)

    fun registerUser(request: RegisterUserRequest): UUID {

        logger.info("KeycloakService::registerUser request: $request")

        val credentialRepresentation = CredentialRepresentation()
        credentialRepresentation.isTemporary = false
        credentialRepresentation.value = request.password

        val userRepresentation = UserRepresentation()
        userRepresentation.isEnabled = true
        userRepresentation.username = request.usernameLower
        userRepresentation.isEmailVerified = true
        userRepresentation.credentials = listOf(credentialRepresentation)
        userRepresentation.requiredActions = emptyList()

        val response = keycloak.realms().realm(USERS_REALM).users().create(userRepresentation)
        val createdUserId = UUID.fromString(CreatedResponseUtil.getCreatedId(response))

        logger.info("KeycloakService::registerUser createdUserId: $createdUserId")

        return createdUserId
    }

    fun getUserToken(username: String, password: String): JwtToken {
        logger.info("KeycloakService::getUserToken username: $username")

        val keycloakUser = KeycloakBuilder.builder()
            .serverUrl(keycloakConfigurationProperties.serverUrl)
            .realm(keycloakConfigurationProperties.realm)
            .clientId(keycloakConfigurationProperties.clientId) // ID of client used for token requests
            .username(username)
            .password(password)
            .grantType(OAuth2Constants.PASSWORD)
            .build()

        val accessToken = keycloakUser.tokenManager().accessToken

        return JwtToken(
            accessToken = accessToken.token,
            refreshToken = accessToken.refreshToken,
            expiresIn = accessToken.expiresIn
        )

    }

    fun resetPassword(userId: UUID, password: String) {
        logger.info("KeycloakService::resetPassword username: $userId")

        val userResource = keycloak.realms().realm(USERS_REALM).users().get(userId.toString())

        val newPassword = CredentialRepresentation()
        newPassword.isTemporary = false
        newPassword.value = password

        userResource.resetPassword(newPassword)

    }

    companion object {
        const val USERS_REALM = "users"
    }

}