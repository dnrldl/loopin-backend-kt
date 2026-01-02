package com.loopin.api.loopinbackend.domain.post.query

import java.time.LocalDateTime

data class PostDetailView(
    val postId: Long,
    val userId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
