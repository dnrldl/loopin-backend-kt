package com.loopin.api.loopinbackend.domain.user.command

data class CheckMyPasswordCommand(
    val userId: Long,
    val password: String
)
