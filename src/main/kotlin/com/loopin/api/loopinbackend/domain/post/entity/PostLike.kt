package com.loopin.api.loopinbackend.domain.post.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "post_likes",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_post_like_post_user",
            columnNames = ["post_id", "user_id"]
        )
    ]
)
class PostLike(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val postId: Long,
    @Column(nullable = false)
    val userId: Long
)