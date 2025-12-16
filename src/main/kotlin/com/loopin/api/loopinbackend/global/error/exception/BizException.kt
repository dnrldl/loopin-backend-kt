package com.loopin.api.loopinbackend.global.error.exception

import com.loopin.api.loopinbackend.global.error.code.ErrorCode

open class BizException(
    val errorCode: ErrorCode,
    val fields: List<String>? = null,
    cause: Throwable? = null,
) : RuntimeException(errorCode.message, cause) {

    override val message: String?
        get() = super.message

    override fun toString(): String = "[${errorCode.code}] ${errorCode.message} fields=$fields"
}