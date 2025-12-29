package com.loopin.api.loopinbackend.domain.user.command

data class UpdateUserPasswordCommand(
    val oldPassword: String,
    val newPassword: String,
    val userId: Long
)
