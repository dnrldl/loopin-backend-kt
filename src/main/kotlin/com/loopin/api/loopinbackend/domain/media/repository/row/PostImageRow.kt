package com.loopin.api.loopinbackend.domain.media.repository.row

class PostImageRow(
    val id: Long,
    val targetId: Long,
    val src: String,
    val description: String?,
    val sortOrder: Int
)