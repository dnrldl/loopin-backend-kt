package com.loopin.api.loopinbackend.common.redis.constant

enum class RedisConstant(
    val value: String
) {
    REFRESH_TOKEN("auth:refresh"),
    BLACKLIST("auth:blacklist"),
    POST("post"),
    LIKE("like")
}