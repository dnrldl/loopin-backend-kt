package com.loopin.api.loopinbackend.domain.user.command.dto

data class UpdateUserPasswordCommand(
    val oldPassword: String,
    val newPassword: String,
    val userId: Long
)
