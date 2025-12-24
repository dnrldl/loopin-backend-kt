package com.loopin.api.loopinbackend.domain.user.dto.req

data class UpdateUserInfoRequest(
    val nickname: String?,
    val bio: String?,
)