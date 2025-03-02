package ru.davidzh.coder.backend.controller.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request to create a new group")
data class CreateGroupRequest(

    @Schema(description = "Название группы")
    val name: String

)

