package com.loopin.api.loopinbackend.domain.user.mapper

import com.loopin.api.loopinbackend.domain.user.dto.res.UserInfoResponse
import com.loopin.api.loopinbackend.domain.user.entity.User

fun User.toInfoResponse(): UserInfoResponse =
    UserInfoResponse(
        id = id ?: 0L,
        email = email
    )