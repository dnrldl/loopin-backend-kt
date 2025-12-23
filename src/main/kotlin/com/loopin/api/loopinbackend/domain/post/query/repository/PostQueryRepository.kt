package com.loopin.api.loopinbackend.domain.post.query.repository

import com.loopin.api.loopinbackend.domain.post.entity.QPost
import com.loopin.api.loopinbackend.domain.post.query.dto.PostDetailView
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class PostQueryRepository(
    private val queryFactory: JPAQueryFactory
) {
    private val post = QPost.post

    fun findPostDetail(postId: Long, userId: Long?): PostDetailView? {
        return queryFactory
            .select(
                Projections.constructor(
                    PostDetailView::class.java,
                    post.id,
                    post.content,
                    post.userId,
                )
            )
            .from(post)
            .where(post.id.eq(postId))
            .fetchOne()
    }
}