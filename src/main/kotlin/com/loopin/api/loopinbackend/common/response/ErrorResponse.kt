package com.loopin.api.loopinbackend.common.response

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.loopin.api.loopinbackend.common.response.code.ErrorCode

@JsonPropertyOrder(
    value = ["success", "code", "message", "error", "responseAt"]
)
class ErrorResponse(
    code: String,
    message: String,
    val error: Any? = null
) : BaseResponse(
    success = false,
    code = code,
    message = message
) {
    companion object {

        fun fail(code: ErrorCode): ErrorResponse =
            ErrorResponse(
                code = code.code,
                message = code.message
            )

        fun fail(code: ErrorCode, errors: Any): ErrorResponse =
            ErrorResponse(
                code = code.code,
                message = code.message,
                error = errors
            )
    }
}

