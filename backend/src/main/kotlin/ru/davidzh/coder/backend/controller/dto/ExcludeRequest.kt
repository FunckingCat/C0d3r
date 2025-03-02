package ru.davidzh.coder.backend.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

data class ExcludeRequest(

    @Schema(description = "Идентификатор пользователя которого надо исключить")
    val memberId: UUID,

    @Schema(description = "Идентификатор группы из которой")
    val groupId: UUID

)

