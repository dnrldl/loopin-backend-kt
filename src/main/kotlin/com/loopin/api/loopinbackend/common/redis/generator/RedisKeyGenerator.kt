package com.loopin.api.loopinbackend.common.redis.generator

import com.loopin.api.loopinbackend.common.redis.constant.RedisPrefix

object RedisKeyGenerator {
    fun generateRedisKey(prefix: RedisPrefix, vararg parts: Any): String = buildString {
        append(prefix.value)
        parts.forEach { append(":").append(it) }
    }
}