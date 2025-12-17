package com.loopin.api.loopinbackend.common.error.exception

import com.loopin.api.loopinbackend.common.error.code.ErrorCode

class BusinessException(
    errorCode: ErrorCode,
    fields: List<String>? = null,
    cause: Throwable? = null,
) : BaseException(errorCode, fields, cause)
