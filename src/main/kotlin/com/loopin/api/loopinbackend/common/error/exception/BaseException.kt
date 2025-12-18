package com.loopin.api.loopinbackend.common.error.exception

import com.loopin.api.loopinbackend.common.response.code.ErrorCode

open class BaseException(
    val errorCode: ErrorCode,
    val fields: List<String>? = null,
    cause: Throwable? = null,
) : RuntimeException(errorCode.message, cause) {

    override val message: String?
        get() = super.message

    override fun toString(): String = "[${errorCode.code}] ${errorCode.message} fields=$fields"
}