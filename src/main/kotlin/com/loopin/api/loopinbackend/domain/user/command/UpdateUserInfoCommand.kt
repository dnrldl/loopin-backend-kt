package com.loopin.api.loopinbackend.domain.user.command

data class UpdateUserInfoCommand(
    val userId: Long,
    val nickname: String?,
    val bio: String?,
)
