package com.loopin.api.loopinbackend.common.response

import com.loopin.api.loopinbackend.common.response.code.SuccessCode

class SuccessResponse<T>(
    code: String,
    message: String,
    val data: T? = null
) : BaseResponse(
    success = true,
    code = code,
    message = message
) {

    companion object {

        fun success(): SuccessResponse<Unit> =
            SuccessResponse(
                code = SuccessCode.REQUEST_SUCCESS.code,
                message = SuccessCode.REQUEST_SUCCESS.message,
            )

        fun <T> success(data: T): SuccessResponse<T> =
            SuccessResponse(
                code = SuccessCode.REQUEST_SUCCESS.code,
                message = SuccessCode.REQUEST_SUCCESS.message,
                data = data
            )

        fun <T> of(
            data: T,
            code: SuccessCode
        ): SuccessResponse<T> =
            SuccessResponse(
                code = code.code,
                message = code.message,
                data = data
            )

        fun of(
            code: SuccessCode
        ): SuccessResponse<Unit> =
            SuccessResponse(
                code = code.code,
                message = code.message,
            )
    }
}
