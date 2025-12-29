package com.loopin.api.loopinbackend.domain.auth.command

data class UserLogoutCommand(
    val userId: Long,
    val accessToken: String?
)
