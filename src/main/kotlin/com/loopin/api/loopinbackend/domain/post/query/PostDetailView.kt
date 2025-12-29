package com.loopin.api.loopinbackend.domain.post.query

import java.time.LocalDateTime

data class PostDetailView(
    val postId: Long,
    val content: String,
    val userId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
