package ru.davidzh.coder.backend.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import ru.davidzh.coder.backend.model.Permission
import java.util.*

@Schema(description = "Request payload for granting or revoking a specific permission for a member within a group. Requires appropriate administrative privileges in the group.")
data class ChangePermissionRequest(

    @field:Schema(
        description = "Specifies whether the permission should be granted (added) or revoked (removed).",
        requiredMode = Schema.RequiredMode.REQUIRED,
        implementation = ChangePermissionAction::class, // Link to the inner enum
        example = "GRANT"
    )
    val action: ChangePermissionAction,

    @field:Schema(
        description = "The unique identifier (UUID) of the group in which the permission change is taking place.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "a1b2c3d4-e5f6-7890-1234-567890abcdef"
    )
    val groupId: UUID,

    @field:Schema(
        description = "The unique identifier (UUID) of the member whose permissions are being changed.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "123e4567-e89b-12d3-a456-426614174000"
    )
    val memberId: UUID,

    @field:Schema(
        description = "The specific permission to be granted or revoked (e.g., 'MANAGE_MEMBERS', 'READ_DETAILS').",
        requiredMode = Schema.RequiredMode.REQUIRED,
        implementation = Permission::class, // Link to your Permission enum/class
        example = "MANAGE_MEMBERS" // Provide an example relevant to your Permission enum
    )
    val permission: Permission // Assuming Permission is an enum defined elsewhere

) {
    @Schema(description = "Defines the action to perform on a permission (grant or revoke).")
    enum class ChangePermissionAction {
        GRANT, REVOKE
    }
}

