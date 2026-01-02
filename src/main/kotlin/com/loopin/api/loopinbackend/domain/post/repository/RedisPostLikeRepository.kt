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
            RedisKeyGenerator.generateRedisKey(RedisConstant.POST, postId, RedisConstant.LIKE)

        return redisTemplate.opsForSet().size(redisKey)
    }

    fun saveLikePost(userId: Long, postId: Long) {
        val redisKey =
            RedisKeyGenerator.generateRedisKey(RedisConstant.POST, postId, RedisConstant.LIKE)

        redisTemplate.opsForSet().add(redisKey, userId.toString())
    }
}