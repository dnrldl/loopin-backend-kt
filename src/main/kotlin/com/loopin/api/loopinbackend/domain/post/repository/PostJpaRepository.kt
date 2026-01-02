package com.loopin.api.loopinbackend.domain.post.repository

import com.loopin.api.loopinbackend.domain.post.entity.Post
import com.loopin.api.loopinbackend.domain.post.query.PostDetailView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostJpaRepository: JpaRepository<Post, Long> {
    fun findPostById(postId: Long): Post?

    @Query(value = """
        SELECT A.ID         as postId
             , A.CREATED_BY as userId
             , A.CONTENT    as content
             , B.src        as imageUrl
             , A.CREATED_AT as createdAt
             , A.UPDATED_AT as updatedAt
          FROM POSTS  A
     LEFT JOIN MEDIAS B ON A.ID = B.TARGET_ID AND B.MEDIA_TYPE = 'POST'
         WHERE A.id = :postId
    """, nativeQuery = true)
    fun findPostDetail(postId: Long, userId: Long?): PostDetailView?
}