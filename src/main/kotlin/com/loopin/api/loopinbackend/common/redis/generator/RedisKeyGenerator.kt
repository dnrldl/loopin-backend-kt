package com.loopin.api.loopinbackend.common.redis.generator

import com.loopin.api.loopinbackend.common.redis.constant.RedisConstant

object RedisKeyGenerator {
    fun generateRedisKey(prefix: RedisConstant, vararg parts: Any): String = buildString {
        append(prefix.value)
        parts.forEach { append(":").append(it) }
    }
}