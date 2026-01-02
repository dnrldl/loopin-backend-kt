package com.loopin.api.loopinbackend.domain.post.query

import java.time.LocalDateTime

class PostDetailView(
    val postId: Long,
    val authorId: Long,
    val content: String,
    val image: List<Image>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    class Image(
        val url: String,
        val description: String?,
        val orderSeq: Int
    )
}
