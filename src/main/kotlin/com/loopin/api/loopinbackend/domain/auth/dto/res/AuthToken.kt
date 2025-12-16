package com.loopin.api.loopinbackend.domain.auth.dto.res

data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
)
