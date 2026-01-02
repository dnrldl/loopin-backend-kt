package com.loopin.api.loopinbackend.domain.post.repository

import com.loopin.api.loopinbackend.domain.post.repository.row.PostDetailRow

interface PostQueryRepository {
    fun findPostDetail(postId: Long, userId: Long?): PostDetailRow?
}