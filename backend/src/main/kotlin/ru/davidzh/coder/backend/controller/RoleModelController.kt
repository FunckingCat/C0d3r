package ru.davidzh.coder.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.davidzh.coder.backend.aop.annotation.LogExecution
import ru.davidzh.coder.backend.controller.dto.ChangePermissionRequest
import ru.davidzh.coder.backend.controller.dto.CreateGroupRequest
import ru.davidzh.coder.backend.controller.dto.ExcludeRequest
import ru.davidzh.coder.backend.controller.dto.JoinGroupRequest
import ru.davidzh.coder.backend.service.UserService
import ru.davidzh.coder.backend.util.extension.asResponseEntity
import java.util.*

/**
 * REST controller for user-related operations.
 *
 * Provides endpoints for managing user interactions within the application.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/role-model")
class RoleModelController(
    private val userService: UserService
) {

    @LogExecution
    @PostMapping("/create-group")
    fun createGroup(@RequestBody createGroupRequest: CreateGroupRequest): ResponseEntity<Any> = userService
        .createGroup(createGroupRequest)
        .asResponseEntity()

    @LogExecution
    @PostMapping("/join-group")
    fun createGroup(@RequestBody joinGroupRequest: JoinGroupRequest): ResponseEntity<Any> = userService
        .joinGroup(joinGroupRequest)
        .asResponseEntity()

    @LogExecution
    @GetMapping("/get-join-group-token/{groupId}")
    fun getJoinGroupToken(@PathVariable groupId: UUID): ResponseEntity<Any> = userService
        .getJoinGroupToken(groupId)
        .asResponseEntity()

    @LogExecution
    @PostMapping("/refresh-join-group-token/{groupId}")
    fun refreshJoinGroupToken(@PathVariable groupId: UUID): ResponseEntity<Any> = userService
        .refreshJoinGroupToken(groupId)
        .asResponseEntity()

    @LogExecution
    @PostMapping("/leave-group/{groupId}")
    fun leaveGroup(@PathVariable groupId: UUID): ResponseEntity<Any> = userService
        .leaveGroup(groupId)
        .asResponseEntity()

    @LogExecution
    @PostMapping("/exclude-member-from-group")
    fun excludeFromGroup(@RequestBody excludeRequest: ExcludeRequest): ResponseEntity<Any> = userService
        .excludeFromGroup(excludeRequest)
        .asResponseEntity()

    @LogExecution
    @PostMapping("/change-permission")
    fun excludeFromGroup(
        @RequestBody changePermissionRequest: ChangePermissionRequest
    ): ResponseEntity<Any> = userService
        .changePermissions(changePermissionRequest)
        .asResponseEntity()

}