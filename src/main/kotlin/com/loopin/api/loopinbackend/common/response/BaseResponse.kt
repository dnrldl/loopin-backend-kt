package com.loopin.api.loopinbackend.common.response

import java.time.LocalDateTime

abstract class BaseResponse(
    val success: Boolean,
    val code: String,
    var message: String,
    val requestAt: LocalDateTime = LocalDateTime.now(),
)