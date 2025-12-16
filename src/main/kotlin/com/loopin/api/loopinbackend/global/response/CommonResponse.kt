package com.loopin.api.loopinbackend.global.response

import com.loopin.api.loopinbackend.global.error.code.ErrorCode
import java.time.LocalDateTime

data class CommonResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: CommonErrorResponse? = null,
    val date: LocalDateTime = LocalDateTime.now()
) {
    companion object {

        fun <T> success(): CommonResponse<T> =
            CommonResponse(success = true)

        fun <T> success(data: T): CommonResponse<T> =
            CommonResponse(success = true, data = data)

        fun fail(errorCode: ErrorCode, fields: Map<String, String>? = null): CommonResponse<Void> {
            val commonErrorResponse = CommonErrorResponse(
                code = errorCode.code,
                message = errorCode.message,
                fields = fields
            )

            return CommonResponse(success = false, error = commonErrorResponse)
        }

    }
}
