package com.loopin.api.loopinbackend.domain.auth.command

data class UserLoginResult(
    val accessToken: String,
    val refreshToken: String,
)