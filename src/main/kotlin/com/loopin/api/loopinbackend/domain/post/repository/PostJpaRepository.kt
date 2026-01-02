package com.loopin.api.loopinbackend.domain.post.repository

import com.loopin.api.loopinbackend.domain.post.entity.Post
import com.loopin.api.loopinbackend.domain.post.query.PostDetailView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostJpaRepository: JpaRepository<Post, Long> {
    @Query(value = """
        SELECT ID         as postId
             , CREATED_BY as userId
             , CONTENT    as content
             , CREATED_AT as createdAt
             , UPDATED_AT as updatedAt
          FROM POSTS A
         WHERE A.id = :postId
    """, nativeQuery = true)
    fun findPostDetail(postId: Long, userId: Long?): PostDetailView?
}