package ru.davidzh.coder.backend.service

import jakarta.ws.rs.client.ClientBuilder
import jakarta.ws.rs.client.Entity
import jakarta.ws.rs.core.Form
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.CreatedResponseUtil
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.GroupRepresentation
import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.davidzh.coder.backend.configuration.properties.KeycloakConfigurationProperties
import ru.davidzh.coder.backend.controller.dto.RegisterUserRequest
import ru.davidzh.coder.backend.controller.model.JwtToken
import ru.davidzh.coder.backend.model.Group
import ru.davidzh.coder.backend.model.GroupDescription
import ru.davidzh.coder.backend.model.GroupMember
import ru.davidzh.coder.backend.model.Permission
import ru.davidzh.coder.backend.util.extension.getUserAuthentication
import java.util.*

@Service
class KeycloakService(
    private val keycloak: Keycloak,
    private val keycloakConfigurationProperties: KeycloakConfigurationProperties,
) {

    private val logger = LoggerFactory.getLogger(KeycloakService::class.java)

    private val realm: RealmResource
        get() = keycloak.realm(USERS_REALM)

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

    fun initializeGroup(groupName: String): UUID {
        val userAuthentication = getUserAuthentication()

        val groupId = createGroupInKeycloak(groupName)
        Permission.entries.forEach { createRoleInKeycloak(it, groupId) }

        addUserToGroup(userAuthentication.userId, groupId)
        Permission.entries.forEach { addRoleToUser(userAuthentication.userId, groupId, it) }

        return groupId
    }

    private fun createGroupInKeycloak(groupName: String): UUID {
        val groupUUID = UUID.randomUUID()
        val groupRepresentation = GroupRepresentation().apply {
            name = groupUUID.toString()
            attributes = mapOf(NAME to listOf(groupName))
        }
        realm.groups().add(groupRepresentation)
        return groupUUID
    }

    private fun createRoleInKeycloak(permission: Permission, groupId: UUID) {
        val roleRepresentation = RoleRepresentation().apply {
            name = "${permission.name}/${groupId}"
            attributes = mapOf(
                NAME to listOf(permission.name),
                RELATED_GROUP to listOf(groupId.toString())
            )
        }
        realm.roles().create(roleRepresentation)
    }

    fun addUserToGroup(userId: UUID, groupId: UUID) {
        val group = realm.groups().groups().find { it.name == groupId.toString() }
            ?: throw IllegalArgumentException("Group not found with ID: $groupId")
        realm.users().get(userId.toString()).joinGroup(group.id)
    }

    fun addRoleToUser(userId: UUID, groupId: UUID, permission: Permission) {
        val roleName = buildRoleName(permission, groupId)
        val role = realm.roles().get(roleName).toRepresentation()
            ?: throw IllegalArgumentException("Role not found with ID: $roleName")
        realm.users().get(userId.toString()).roles().realmLevel().add(listOf(role))
    }

    fun removeUserFromGroup(userId: UUID, groupId: UUID) {
        val group = findGroupById(groupId)
        realm.users().get(userId.toString()).leaveGroup(group.id)
        Permission.entries.forEach { removeRoleFromUser(userId, groupId, it) }
    }

    fun removeRoleFromUser(userId: UUID, groupId: UUID, permission: Permission) {
        val userResource = realm.users().get(userId.toString())
        val rolesToRemove = realm.roles().list().stream()
            .filter { it.name == buildRoleName(permission, groupId) }
            .toList()

        if (rolesToRemove.isEmpty()) {
            logger.info("Permission $permission is not found in user $userId")
        }

        userResource.roles().realmLevel().remove(rolesToRemove)
    }

    fun describeGroup(groupId: UUID): GroupDescription {
        val group = findGroupById(groupId)
        val groupMembers = realm.groups().group(group.id).members()
            .map { realm.users().get(it.id) }
            .map { userResource ->
                val userRepresentation = userResource.toRepresentation()
                val roles = userResource.roles().realmLevel().listEffective()
                    .map { it.id }
                    .map { realm.rolesById().getRole(it) }
                    .toSet()
                GroupMember(
                    id = UUID.fromString(userRepresentation.id),
                    username = userRepresentation.username,
                    permissions = calcPermissions(groupId.toString(), roles)
                )
            }
            .toSet()
        return GroupDescription(
            id = groupId,
            name = group.attributes[NAME]?.first()?.toString() ?: "",
            members = groupMembers
        )
    }

    private fun findGroupById(groupId: UUID): GroupRepresentation {
        return realm.getGroupByPath(groupId.toString())
            ?: throw NoSuchElementException("Group not found with ID: $groupId")
    }

    fun getUserGroups(userId: UUID): Set<Group> {
        val userResource = realm.users().get(userId.toString())
        val roles = userResource.roles().realmLevel().listEffective()
            .map { it.id }
            .map { realm.rolesById().getRole(it) }
            .toSet()
        val groups = userResource.groups().map { it.name }.map { realm.getGroupByPath(it) }.toSet()
        return groups
            .map {
                Group(
                    name = it.attributes?.get(NAME)?.first().toString(),
                    id = UUID.fromString(it.name),
                    permissions = calcPermissions(it.name, roles)
                )
            }
            .toSet()
    }

    private fun calcPermissions(groupName: String, roles: Set<RoleRepresentation>): Set<Permission> =
        roles.asSequence()
            .filter { it.attributes?.get(RELATED_GROUP)?.first() == groupName }
            .mapNotNull { it.attributes?.get(NAME)?.first() }
            .map { Permission.valueOf(it) }
            .toSet()

    private fun buildRoleName(permission: Permission, groupId: UUID) = "${permission.name}/${groupId}"

    data class TokenResponse(
        val access_token: String,
        val expires_in: Int,
        val refresh_expires_in: Int,
        val refresh_token: String,
        val token_type: String,
        val session_state: String?,
        val scope: String
    )

    fun refreshToken(refreshToken: String): TokenResponse {
        val response = ClientBuilder.newClient()
            .target("${keycloakConfigurationProperties.serverUrl}/realms/$USERS_REALM/protocol/openid-connect/token")
            .request()
            .post(Entity.form(
                Form().apply {
                    param("grant_type", "refresh_token")
                    param("refresh_token", refreshToken)
                    param("client_id", keycloakConfigurationProperties.clientId)
                }
            ))

        return if (response.status == 200) {
            response.readEntity(TokenResponse::class.java)
        } else {
            throw RuntimeException("Token refresh failed: ${response.readEntity(String::class.java)}")
        }
    }

    companion object {
        const val USERS_REALM = "users"
        const val RELATED_GROUP = "relatedGroup"
        const val NAME = "name"
    }

}