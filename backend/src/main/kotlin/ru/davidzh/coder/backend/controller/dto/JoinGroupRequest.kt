package ru.davidzh.coder.backend.controller.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request to join group")
data class JoinGroupRequest(

    @Schema(description = "Group access token")
    val token: String

)

