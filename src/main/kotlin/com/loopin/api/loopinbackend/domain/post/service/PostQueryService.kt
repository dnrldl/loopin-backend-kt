package com.loopin.api.loopinbackend.domain.post.service

import com.loopin.api.loopinbackend.common.error.exception.BusinessException
import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import com.loopin.api.loopinbackend.domain.media.repository.MediaQueryRepository
import com.loopin.api.loopinbackend.domain.post.query.PostDetailView
import com.loopin.api.loopinbackend.domain.post.repository.PostQueryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostQueryService(
    private val postQueryRepository: PostQueryRepository,
    private val mediaQueryRepository: MediaQueryRepository
) {
    fun getPostDetail(postId: Long, userId: Long?): PostDetailView {
        val post = postQueryRepository.findPostDetail(postId, userId) ?: throw BusinessException(ErrorCode.POST_NOT_FOUND)
        val images = mediaQueryRepository.findPostImagesByTargetId(post.id)
        val image = images.map {
                PostDetailView.Image(
                    url = it.src,
                    description = it.description,
                    orderSeq = it.sortOrder
                )
            }

        return PostDetailView(
            postId = post.id,
            authorId = post.createdBy,
            content = post.content ?: "",
            image = image,
            createdAt = post.createdAt,
            updatedAt = post.updatedAt
        )
    }

}