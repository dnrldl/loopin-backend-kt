package com.loopin.api.loopinbackend.domain.post.repository

import com.loopin.api.loopinbackend.domain.post.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostJpaRepository: JpaRepository<Post, Long> {
}