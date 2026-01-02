package com.loopin.api.loopinbackend.domain.post.repository

import com.loopin.api.loopinbackend.domain.post.entity.QPost
import com.loopin.api.loopinbackend.domain.post.repository.row.PostDetailRow
import com.loopin.api.loopinbackend.domain.post.repository.row.QPostDetailRow
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class PostQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : PostQueryRepository {
    private val post = QPost.post

    override fun findPostDetail(postId: Long, userId: Long?): PostDetailRow? {
        // TODO: 게시글 좋아요
        return queryFactory
            .select(
                QPostDetailRow(
                    post.id,
                    post.createdBy,
                    post.content,
                    post.createdAt,
                    post.updatedAt
                )
            )
            .from(post)
            .where(post.id.eq(postId))
            .fetchOne()
    }
}