package com.loopin.api.loopinbackend.common.redis.constant

enum class RedisPrefix(
    val value: String
) {
    REFRESH_TOKEN("auth:refresh"),
    BLACKLIST("auth:blacklist"),
}