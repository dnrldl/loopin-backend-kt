package com.loopin.api.loopinbackend.domain.user.command

data class UpdateUserInfoResult(
    val userId: Long,
    val nickname: String?,
    val bio: String?,
)
