package com.loopin.api.loopinbackend.domain.auth.dto

data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
)