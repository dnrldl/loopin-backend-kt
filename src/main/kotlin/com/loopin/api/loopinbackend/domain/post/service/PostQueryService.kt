package com.loopin.api.loopinbackend.domain.post.service

import com.loopin.api.loopinbackend.common.error.exception.BusinessException
import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import com.loopin.api.loopinbackend.domain.post.query.PostDetailView
import com.loopin.api.loopinbackend.domain.post.repository.PostQueryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostQueryService(
    private val postQueryRepository: PostQueryRepository
) {
    fun getPostDetail(postId: Long, userId: Long?): PostDetailView =
        postQueryRepository.findPostDetail(postId, userId) ?: throw BusinessException(ErrorCode.POST_NOT_FOUND)
}