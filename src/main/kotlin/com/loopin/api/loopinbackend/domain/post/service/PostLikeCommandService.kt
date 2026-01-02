package com.loopin.api.loopinbackend.domain.post.service

import com.loopin.api.loopinbackend.domain.post.command.LikePostCommand
import com.loopin.api.loopinbackend.domain.post.entity.PostLike
import com.loopin.api.loopinbackend.domain.post.repository.PostLikeJpaRepository
import com.loopin.api.loopinbackend.domain.post.repository.RedisPostLikeRepository
import org.springframework.stereotype.Service

@Service
class PostLikeCommandService(
    private val postLikeJpaRepository: PostLikeJpaRepository,
    private val redisPostLikeRepository: RedisPostLikeRepository
) {

    fun likePost(command: LikePostCommand) {
        postLikeJpaRepository.save(
            PostLike(
                userId = command.userId,
                postId = command.postId,
            )
        )

        // redis 저장
        redisPostLikeRepository.saveLikePost(command.userId, command.postId)
    }
}