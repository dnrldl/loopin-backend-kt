package com.loopin.api.loopinbackend.domain.auth.mapper

import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import com.loopin.api.loopinbackend.domain.auth.type.TokenStatus

fun TokenStatus.toErrorCode(): ErrorCode =
    when (this) {
        TokenStatus.EMPTY -> ErrorCode.EMPTY_JWT
        TokenStatus.EXPIRED -> ErrorCode.EXPIRED_JWT
        TokenStatus.INVALID -> ErrorCode.INVALID_JWT
        TokenStatus.ERROR -> ErrorCode.JWT_VALIDATION_ERROR
        TokenStatus.VALID -> throw IllegalStateException("VALID는 에러가 아님")
    }
