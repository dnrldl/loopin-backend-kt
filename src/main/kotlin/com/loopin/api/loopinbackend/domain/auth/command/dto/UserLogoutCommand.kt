package com.loopin.api.loopinbackend.domain.auth.command.dto

data class UserLogoutCommand(
    val userId: Long,
    val accessToken: String?
)
