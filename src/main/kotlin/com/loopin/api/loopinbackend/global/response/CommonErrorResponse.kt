package com.loopin.api.loopinbackend.global.response

data class CommonErrorResponse(
    val code: String,
    val message: String,
    val fields: Map<String, String>? = null
)