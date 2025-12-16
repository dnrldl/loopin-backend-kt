package com.loopin.api.loopinbackend.domain.auth.type

enum class TokenStatus {
    VALID,
    EMPTY,
    EXPIRED,
    INVALID,
    ERROR
}