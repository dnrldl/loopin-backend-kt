package com.loopin.api.loopinbackend.domain.post.command

data class LikePostCommand(
    val userId: Long,
    val postId: Long
)