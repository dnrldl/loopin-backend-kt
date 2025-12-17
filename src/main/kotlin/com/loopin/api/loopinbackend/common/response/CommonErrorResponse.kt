package com.loopin.api.loopinbackend.common.response

data class CommonErrorResponse(
    val code: String,
    val message: String,
    val fields: Map<String, String>? = null
)