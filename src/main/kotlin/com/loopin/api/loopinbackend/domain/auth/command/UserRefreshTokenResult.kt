package com.loopin.api.loopinbackend.domain.auth.command

data class UserRefreshTokenResult(
    val accessToken: String,
    val refreshToken: String
)