package com.loopin.api.loopinbackend.domain.post.service

import com.loopin.api.loopinbackend.common.error.exception.BusinessException
import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import com.loopin.api.loopinbackend.domain.post.query.PostDetailView
import com.loopin.api.loopinbackend.domain.post.repository.PostJpaRepository
import com.loopin.api.loopinbackend.domain.post.repository.PostQueryRepositoryImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostQueryService(
    private val postQueryRepository: PostQueryRepositoryImpl,
    private val postJpaRepository: PostJpaRepository
) {
    fun getPostDetail(postId: Long, userId: Long?): PostDetailView =
        postJpaRepository.findPostDetail(postId, userId) ?: throw BusinessException(ErrorCode.POST_NOT_FOUND)
}