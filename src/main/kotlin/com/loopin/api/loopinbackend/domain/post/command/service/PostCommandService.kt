package com.loopin.api.loopinbackend.domain.post.command.service

import com.loopin.api.loopinbackend.domain.post.command.dto.CreatePostResult
import com.loopin.api.loopinbackend.domain.post.dto.req.CreatePostRequest
import com.loopin.api.loopinbackend.domain.post.entity.Post
import com.loopin.api.loopinbackend.domain.post.repository.PostJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostCommandService(
    private val postJpaRepository: PostJpaRepository,
) {
    fun createPost(command: CreatePostRequest, userId: Long): CreatePostResult {
        val post = Post.create(command.content, userId)
        val savedPost = postJpaRepository.save(post)

        return CreatePostResult(postId = savedPost.id!!)
    }
}