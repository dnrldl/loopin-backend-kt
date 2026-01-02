package com.loopin.api.loopinbackend.domain.media.repository

import com.loopin.api.loopinbackend.domain.media.entity.Media
import com.loopin.api.loopinbackend.domain.media.type.MediaType
import org.springframework.data.jpa.repository.JpaRepository

interface MediaJpaRepository : JpaRepository<Media, Long> {
    fun findByMediaTypeAndTargetId(mediaType: MediaType, targetId: Long): List<Media>
}