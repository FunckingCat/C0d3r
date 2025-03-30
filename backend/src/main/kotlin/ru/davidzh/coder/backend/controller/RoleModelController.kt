package ru.davidzh.coder.backend.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.davidzh.coder.backend.aop.annotation.LogExecution
import ru.davidzh.coder.backend.controller.dto.ChangePermissionRequest
import ru.davidzh.coder.backend.controller.dto.CreateGroupRequest
import ru.davidzh.coder.backend.controller.dto.ExcludeRequest
import ru.davidzh.coder.backend.controller.dto.JoinGroupRequest
import ru.davidzh.coder.backend.controller.model.RestError
import ru.davidzh.coder.backend.model.GroupDescription
import ru.davidzh.coder.backend.model.JoinGroupToken
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
@Tag(name = "Role & Group Management", description = "Endpoints for managing user groups, memberships, and permissions.")
class RoleModelController(
    private val userService: UserService
) {

    @LogExecution
    @PostMapping("/create-group")
    @Operation(
        summary = "Create Group",
        description = "Creates a new group with the requesting user as the initial owner/admin."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Group created successfully, returns the new group's ID.",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = UUID::class))]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error during group creation.",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun createGroup(@RequestBody createGroupRequest: CreateGroupRequest): ResponseEntity<UUID> = userService
        .createGroup(createGroupRequest)
        .asResponseEntity()

    @LogExecution
    @GetMapping("/group/{groupId}")
    @Operation(
        summary = "Describe Group",
        description = "Retrieves detailed information about a specific group by its ID."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Successfully retrieved group details.",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = GroupDescription::class))]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error or group not found.", // Grouping 404 into 500 per constraint
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun createGroup(@PathVariable groupId: UUID) = userService
        .describeGroup(groupId)
        .asResponseEntity()

    @LogExecution
    @PostMapping("/join-group")
    @Operation(
        summary = "Join Group",
        description = "Allows a user to join a group, typically using a join token or by direct invitation ID."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Successfully joined the group.",
            content = [Content()]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error, invalid token, group not found, or user already in group.",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun createGroup(@RequestBody joinGroupRequest: JoinGroupRequest): ResponseEntity<Unit> = userService
        .joinGroup(joinGroupRequest)
        .asResponseEntity()

    @LogExecution
    @GetMapping("/get-join-group-token/{groupId}")
    @Operation(
        summary = "Get Join Group Token",
        description = "Retrieves a join token for a specific group (requires appropriate permissions, e.g., group owner/admin)."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Successfully retrieved join token.",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = JoinGroupToken::class))]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error, group not found, or insufficient permissions.",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun getJoinGroupToken(@PathVariable groupId: UUID): ResponseEntity<JoinGroupToken> = userService
        .getJoinGroupToken(groupId)
        .asResponseEntity()

    @LogExecution
    @PostMapping("/refresh-join-group-token/{groupId}")
    @Operation(
        summary = "Refresh Join Group Token",
        description = "Refreshes (invalidates old, creates new) the join token for a specific group (requires appropriate permissions)."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Successfully refreshed the join token.",
            content = [Content()]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error, group not found, or insufficient permissions.",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun refreshJoinGroupToken(@PathVariable groupId: UUID): ResponseEntity<Unit> = userService
        .refreshJoinGroupToken(groupId)
        .asResponseEntity()

    @LogExecution
    @PostMapping("/leave-group/{groupId}")
    @Operation(
        summary = "Leave Group",
        description = "Allows the requesting user to leave a group they are currently a member of."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Successfully left the group.",
            content = [Content()]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error, group not found, or user not a member of the group.",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun leaveGroup(@PathVariable groupId: UUID): ResponseEntity<Unit> = userService
        .leaveGroup(groupId)
        .asResponseEntity()

    @LogExecution
    @PostMapping("/exclude-member-from-group")
    @Operation(
        summary = "Exclude Member From Group",
        description = "Allows an authorized user (e.g., owner/admin) to remove another member from a group."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Successfully excluded the member from the group.",
            content = [Content()]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error, group/member not found, or insufficient permissions.",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun excludeFromGroup(@RequestBody excludeRequest: ExcludeRequest): ResponseEntity<Unit> = userService
        .excludeFromGroup(excludeRequest)
        .asResponseEntity()

    @LogExecution
    @PostMapping("/change-permission")
    @Operation(
        summary = "Change Member Permissions",
        description = "Allows an authorized user (e.g., owner/admin) to change the permissions/role of another member within a group."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Successfully changed the member's permissions.",
            content = [Content()]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error, group/member not found, invalid permissions specified, or insufficient permissions.",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun excludeFromGroup(
        @RequestBody changePermissionRequest: ChangePermissionRequest
    ): ResponseEntity<Unit> = userService
        .changePermissions(changePermissionRequest)
        .asResponseEntity()

}