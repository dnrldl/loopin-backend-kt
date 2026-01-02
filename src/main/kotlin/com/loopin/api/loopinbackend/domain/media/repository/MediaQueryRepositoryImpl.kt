package com.loopin.api.loopinbackend.domain.media.repository

import com.loopin.api.loopinbackend.domain.media.entity.QMedia
import com.loopin.api.loopinbackend.domain.media.repository.row.PostImageRow
import com.loopin.api.loopinbackend.domain.media.type.MediaType
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class MediaQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : MediaQueryRepository {

    private val media = QMedia.media

    override fun findPostImagesByTargetId(targetId: Long): List<PostImageRow> {
        return queryFactory.select(
            Projections.constructor(
                PostImageRow::class.java,
                media.id,
                media.targetId,
                media.src,
                media.description,
                media.sortOrder
            )
        )
            .from(media)
            .where(
                media.mediaType.eq(MediaType.POST_IMAGE),
                media.targetId.eq(targetId)
            )
            .orderBy(media.sortOrder.asc())
            .fetch()
    }
}