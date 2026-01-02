package com.loopin.api.loopinbackend.domain.media.repository

import com.loopin.api.loopinbackend.domain.media.repository.row.PostImageRow

interface MediaQueryRepository {
    fun findPostImagesByTargetId(targetId: Long): List<PostImageRow>
}