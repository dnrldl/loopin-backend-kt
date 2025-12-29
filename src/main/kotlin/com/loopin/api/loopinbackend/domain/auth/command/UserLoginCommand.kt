package com.loopin.api.loopinbackend.domain.auth.command

data class UserLoginCommand(
    val email: String,
    val password: String
)
