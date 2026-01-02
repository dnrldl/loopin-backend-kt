package com.loopin.api.loopinbackend.domain.post.repository

import com.loopin.api.loopinbackend.domain.post.entity.PostLike
import org.springframework.data.jpa.repository.JpaRepository

interface PostLikeJpaRepository: JpaRepository<PostLike, Long> {
    fun deleteByPostIdAndUserId(postId: Long, userId: Long): Int
}