package com.loopin.api.loopinbackend.domain.auth.dto.res

data class UserRefreshTokenResult(
    val accessToken: String,
    val refreshToken: String
)
