package com.loopin.api.loopinbackend.domain.auth.infra.redis

import com.loopin.api.loopinbackend.common.redis.constant.RedisPrefix
import com.loopin.api.loopinbackend.common.redis.generator.RedisKeyGenerator
import com.loopin.api.loopinbackend.common.security.jwt.JwtProvider
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration
import java.util.concurrent.TimeUnit

@Repository
class RedisAuthTokenRepository(
    private val jwtProvider: JwtProvider,
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun saveRefreshToken(userId: Long, refreshToken: String) {
        val redisKey =
            RedisKeyGenerator.generateRedisKey(RedisPrefix.REFRESH_TOKEN, userId)
        val ttl = Duration.ofMillis(jwtProvider.extractExpiredTime(refreshToken) - System.currentTimeMillis()).seconds

        redisTemplate.opsForValue()
            .set(redisKey, refreshToken, ttl, TimeUnit.SECONDS)
    }

    fun findRefreshToken(userId: Long): String? {
        val redisKey =
            RedisKeyGenerator.generateRedisKey(RedisPrefix.REFRESH_TOKEN, userId)

        return redisTemplate.opsForValue().get(redisKey)
    }

    fun deleteRefreshToken(userId: Long) {
        val redisKey =
            RedisKeyGenerator.generateRedisKey(RedisPrefix.REFRESH_TOKEN, userId)

        redisTemplate.delete(redisKey)
    }

    fun saveBlacklistToken(userId: Long, blacklistToken: String) {
        val redisKey =
            RedisKeyGenerator.generateRedisKey(RedisPrefix.BLACKLIST, blacklistToken)
        val ttl = Duration.ofMillis(jwtProvider.extractExpiredTime(blacklistToken) - System.currentTimeMillis()).seconds

        if (ttl > 0) {
            redisTemplate.opsForValue()
                .set(redisKey, userId.toString(), ttl, TimeUnit.SECONDS)
        }
    }

    fun hasBlacklistToken(accessToken: String): Boolean =
        redisTemplate.hasKey(RedisKeyGenerator.generateRedisKey(RedisPrefix.BLACKLIST, accessToken))
}