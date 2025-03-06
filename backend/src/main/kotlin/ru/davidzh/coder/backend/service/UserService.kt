package ru.davidzh.coder.backend.service

import org.springframework.stereotype.Service
import ru.davidzh.coder.backend.controller.dto.ChangePermissionRequest
import ru.davidzh.coder.backend.controller.dto.CreateGroupRequest
import ru.davidzh.coder.backend.controller.dto.ExcludeRequest
import ru.davidzh.coder.backend.controller.dto.JoinGroupRequest
import ru.davidzh.coder.backend.dao.entity.GroupAccessTokenEntity
import ru.davidzh.coder.backend.dao.repository.GroupAccessTokenRepository
import ru.davidzh.coder.backend.dao.repository.UserRepository
import ru.davidzh.coder.backend.model.JoinGroupToken
import ru.davidzh.coder.backend.model.Permission
import ru.davidzh.coder.backend.model.User
import ru.davidzh.coder.backend.util.extension.getUserAuthentication
import ru.davidzh.coder.backend.util.extension.toBase64
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val groupAccessTokenRepository: GroupAccessTokenRepository,
    private val keycloakService: KeycloakService
) {

    fun getCurrentUser(): User = getUserAuthentication()
        .let { it to userRepository.findByUserId(it.userId) }
        .let {
            User(
                id = it.first.userId,
                username = it.second.map { entity -> entity.username }.orElse("Anonimus"),
                roles = it.first.roles.toList(),
                groups = keycloakService.getUserGroups(it.first.userId)
            )
        }

    fun createGroup(createGroupRequest: CreateGroupRequest):UUID {
        val groupId = keycloakService.initializeGroup(createGroupRequest.name)
        val groupAccessTokenEntity = GroupAccessTokenEntity(
            groupId = groupId,
            accessToken = generateGroupAccessToken()
        )
        groupAccessTokenRepository.save(groupAccessTokenEntity)
        return groupId
    }

    fun leaveGroup(groupId: UUID) {
        val user = getUserAuthentication()
        keycloakService.removeUserFromGroup(user.userId, groupId)
    }

    fun excludeFromGroup(excludeRequest: ExcludeRequest) {
        checkUserIsAdmin(excludeRequest.groupId)
        keycloakService.removeUserFromGroup(excludeRequest.memberId, excludeRequest.groupId)
    }

    fun changePermissions(changePermissionRequest: ChangePermissionRequest) {
        checkUserIsAdmin(changePermissionRequest.groupId)
        when (changePermissionRequest.action) {
            ChangePermissionRequest.ChangePermissionAction.GRANT -> keycloakService
                .addRoleToUser(
                    changePermissionRequest.memberId,
                    changePermissionRequest.groupId,
                    changePermissionRequest.permission
                )
            ChangePermissionRequest.ChangePermissionAction.REVOKE -> keycloakService
                .removeRoleFromUser(
                    changePermissionRequest.memberId,
                    changePermissionRequest.groupId,
                    changePermissionRequest.permission
                )
        }
    }

    fun joinGroup(joinGroupRequest: JoinGroupRequest) {
        val tokenEntity = groupAccessTokenRepository.findByAccessToken(joinGroupRequest.token)
        checkNotNull(tokenEntity) { "Invalid Access Token" }
        val user = getUserAuthentication()
        keycloakService.addUserToGroup(user.userId, tokenEntity.groupId)
        keycloakService.addRoleToUser(user.userId, tokenEntity.groupId, Permission.VIEW)
    }

    fun getJoinGroupToken(groupId: UUID): JoinGroupToken {
        checkUserIsAdmin(groupId)
        val tokenEntity = groupAccessTokenRepository.findByGroupId(groupId)
        checkNotNull(tokenEntity) { "Group not found" }
        return JoinGroupToken(
            groupId = tokenEntity.groupId,
            token = tokenEntity.accessToken
        )
    }

    fun refreshJoinGroupToken(groupId: UUID) {
        checkUserIsAdmin(groupId)
        val tokenEntity = groupAccessTokenRepository.findByGroupId(groupId)
        checkNotNull(tokenEntity) { "Group not found" }
        groupAccessTokenRepository.save(tokenEntity.copy(accessToken = generateGroupAccessToken()))
    }

    private fun checkUserIsAdmin(groupId: UUID) {
        val user = getCurrentUser()
        if (!user.groups.first { it.id == groupId }.permissions.contains(Permission.ADMIN)) {
            throw IllegalAccessError("Poor permissions to perform this operation")
        }
    }

    private fun generateGroupAccessToken(): String = UUID.randomUUID().toString().toBase64()

}