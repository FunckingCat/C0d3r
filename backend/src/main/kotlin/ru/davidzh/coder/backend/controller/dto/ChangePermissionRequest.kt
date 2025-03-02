package ru.davidzh.coder.backend.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import ru.davidzh.coder.backend.model.Permission
import java.util.*

data class ChangePermissionRequest(

    @Schema(description = "Идентификатор пользователя которого надо исключить")
    val action: ChangePermissionAction,

    @Schema(description = "Идентификатор группы из которой")
    val groupId: UUID,

    @Schema(description = "Идентификатор группы из которой")
    val memberId: UUID,

    @Schema(description = "Идентификатор группы из которой")
    val permission: Permission

) {
    enum class ChangePermissionAction {
        GRANT, REVOKE
    }
}

