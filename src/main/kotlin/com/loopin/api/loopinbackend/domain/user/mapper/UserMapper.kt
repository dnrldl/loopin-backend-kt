package com.loopin.api.loopinbackend.domain.user.mapper

import com.loopin.api.loopinbackend.domain.user.query.UserInfoView
import com.loopin.api.loopinbackend.domain.user.entity.User

fun User.toInfoView(): UserInfoView =
    UserInfoView(
        id = id ?: 0L,
        email = email
    )