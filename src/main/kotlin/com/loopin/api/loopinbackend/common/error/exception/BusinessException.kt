package com.loopin.api.loopinbackend.common.error.exception

import com.loopin.api.loopinbackend.common.response.code.ErrorCode

class BusinessException(
    errorCode: ErrorCode,
    fields: Any? = null,
    cause: Throwable? = null,
) : BaseException(errorCode, fields, cause)
