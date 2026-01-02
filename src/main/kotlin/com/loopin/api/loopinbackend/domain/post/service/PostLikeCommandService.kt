package com.loopin.api.loopinbackend.domain.post.service

import com.loopin.api.loopinbackend.common.error.exception.BusinessException
import com.loopin.api.loopinbackend.common.logging.logger
import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import com.loopin.api.loopinbackend.domain.post.entity.PostLike
import com.loopin.api.loopinbackend.domain.post.repository.PostJpaRepository
import com.loopin.api.loopinbackend.domain.post.repository.PostLikeJpaRepository
import com.loopin.api.loopinbackend.domain.post.repository.RedisPostLikeRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.redis.RedisSystemException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostLikeCommandService(
    private val postJpaRepository: PostJpaRepository,
    private val postLikeJpaRepository: PostLikeJpaRepository,
    private val redisPostLikeRepository: RedisPostLikeRepository
) {

    private val log = logger()

    fun likePost(postId: Long, userId: Long) {
        if (!postJpaRepository.existsById(postId)) throw BusinessException(ErrorCode.POST_NOT_FOUND)

        try {
            postLikeJpaRepository.save(
                PostLike(
                    userId = userId,
                    postId = postId
                )
            )
        } catch (e: DataIntegrityViolationException) {
            throw BusinessException(ErrorCode.ALREADY_LIKED_POST)
        }

        try {
            redisPostLikeRepository.increment(postId)
        } catch (e: RedisSystemException) {
            log.warn("Redis 좋아요 수 증가 실패 postId=$postId", e)
        }
    }

    fun unlikePost(postId: Long, userId: Long) {
        if (!postJpaRepository.existsById(postId)) throw BusinessException(ErrorCode.POST_NOT_FOUND)

        val deletedRow = postLikeJpaRepository.deleteByPostIdAndUserId(postId, userId)
        if (deletedRow == 0) throw BusinessException(ErrorCode.ALREADY_UNLIKED_POST)

        try {
            redisPostLikeRepository.decrement(postId)
        } catch (e: RedisSystemException) {
            log.warn("Redis 좋아요 수 감소 실패 postId=$postId", e)
        }
    }
}