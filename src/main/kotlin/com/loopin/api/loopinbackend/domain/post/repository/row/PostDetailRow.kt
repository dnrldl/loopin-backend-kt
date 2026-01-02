package com.loopin.api.loopinbackend.domain.post.repository.row

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

class PostDetailRow @QueryProjection constructor(
    val id: Long,
    val createdBy: Long,
    val content: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)