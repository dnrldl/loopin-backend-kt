package com.loopin.api.loopinbackend.domain.auth.command.dto

data class UserRefreshTokenCommand(
    val refreshToken: String
)