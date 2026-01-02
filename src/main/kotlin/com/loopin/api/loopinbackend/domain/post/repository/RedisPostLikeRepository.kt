package com.loopin.api.loopinbackend.domain.post.repository

import com.loopin.api.loopinbackend.common.redis.constant.RedisConstant
import com.loopin.api.loopinbackend.common.redis.generator.RedisKeyGenerator
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisPostLikeRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun findPostLikeCount(postId: Long): Long {
        val redisKey =
            RedisKeyGenerator.generateRedisKey(RedisConstant.POST, postId, RedisConstant.LIKE_COUNT)

        return redisTemplate.opsForValue().get(redisKey)?.toLong() ?: 0L
    }

    fun increment(postId: Long) {
        val redisKey =
            RedisKeyGenerator.generateRedisKey(RedisConstant.POST, postId, RedisConstant.LIKE_COUNT)

        redisTemplate.opsForValue().increment(redisKey)
    }

    fun decrement(postId: Long) {
        val redisKey =
            RedisKeyGenerator.generateRedisKey(RedisConstant.POST, postId, RedisConstant.LIKE_COUNT)

        redisTemplate.opsForValue().decrement(redisKey)
    }
}