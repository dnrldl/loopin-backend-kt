package com.loopin.api.loopinbackend.domain.post.repository

import com.loopin.api.loopinbackend.domain.post.entity.QPost
import com.loopin.api.loopinbackend.domain.post.query.PostDetailView
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
class PostQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : PostQueryRepository {
    private val post = QPost.post

    override fun findPostDetail(postId: Long, userId: Long?): PostDetailView? {
        return queryFactory
            .select(
                Projections.constructor(
                    PostDetailView::class.java,
                    post.id,
                    post.content,
                )
            )
            .from(post)
            .where(post.id.eq(postId))
            .fetchOne()
    }
}