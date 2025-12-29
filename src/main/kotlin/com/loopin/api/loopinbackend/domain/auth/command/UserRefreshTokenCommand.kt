package com.loopin.api.loopinbackend.domain.auth.command

data class UserRefreshTokenCommand(
    val refreshToken: String
)