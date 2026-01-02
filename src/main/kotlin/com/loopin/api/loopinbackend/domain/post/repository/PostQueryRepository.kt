package com.loopin.api.loopinbackend.domain.post.repository

import com.loopin.api.loopinbackend.domain.post.query.PostDetailView

interface PostQueryRepository {
    fun findPostDetail(postId: Long, userId: Long?): PostDetailView?
}