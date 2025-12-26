package com.loopin.api.loopinbackend.domain.auth.command.dto

data class UserLoginCommand(
    val email: String,
    val password: String
)
