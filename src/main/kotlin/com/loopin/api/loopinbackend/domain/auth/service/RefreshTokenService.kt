package com.loopin.api.loopinbackend.domain.auth.service

import com.loopin.api.loopinbackend.common.redis.constant.RedisPrefix
import com.loopin.api.loopinbackend.common.redis.generator.RedisKeyGenerator
import com.loopin.api.loopinbackend.common.security.jwt.JwtProvider
import com.loopin.api.loopinbackend.domain.auth.entity.RefreshToken
import com.loopin.api.loopinbackend.domain.auth.repository.RefreshTokenRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtProvider: JwtProvider,
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun registerRefreshToken(token: String) {
        val userId = jwtProvider.extractUserId(token)
        val expiredMillis = jwtProvider.extractExpiredTime(token)
        val redisKey = RedisKeyGenerator.generateRedisKey(RedisPrefix.REFRESH_TOKEN, userId)

        val expiresAt = Instant.ofEpochMilli(expiredMillis)
            .atZone(ZoneOffset.UTC)
            .toLocalDateTime()
        val ttlSeconds = Duration.ofMillis(expiredMillis - System.currentTimeMillis()).seconds

        check(ttlSeconds > 0) {
            "생성된 리프레시 토큰이 이미 만료되었습니다. expiresAt=$expiresAt"
        }

        redisTemplate.opsForValue().set(redisKey, token, ttlSeconds, TimeUnit.SECONDS)

        val refreshToken = RefreshToken(
            userId = userId,
            tokenValue = token,
            expiresAt = expiresAt
        )

        refreshTokenRepository.deleteByUserId(userId)
        refreshTokenRepository.save(refreshToken)
    }
}