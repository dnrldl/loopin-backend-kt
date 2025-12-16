package com.loopin.api.loopinbackend.domain.auth.dto.req

data class UserLoginRequest(
    val email: String,
    val password: String
)