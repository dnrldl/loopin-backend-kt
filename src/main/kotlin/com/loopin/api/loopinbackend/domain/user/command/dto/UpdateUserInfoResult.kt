package com.loopin.api.loopinbackend.domain.user.command.dto

data class UpdateUserInfoResult(
    val userId: Long,
    val nickname: String?,
    val bio: String?,
)
