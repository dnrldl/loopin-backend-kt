package com.loopin.api.loopinbackend.domain.post.entity

import com.loopin.api.loopinbackend.common.entity.BaseEntity
import jakarta.persistence.*

@Table(name = "posts")
@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    val content: String? = null,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val likeCount: Int = 0
) : BaseEntity() {
    companion object {
        fun create(content: String?, userId: Long): Post =
            Post(
                content = content,
                userId = userId
            )
    }
}