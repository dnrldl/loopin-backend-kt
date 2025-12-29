package com.loopin.api.loopinbackend.domain.post.service

import com.loopin.api.loopinbackend.domain.post.command.CreatePostResult
import com.loopin.api.loopinbackend.domain.post.entity.Post
import com.loopin.api.loopinbackend.domain.post.repository.PostJpaRepository
import com.loopin.api.loopinbackend.domain.post.web.req.CreatePostRequest
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

        return CreatePostResult(postId = checkNotNull(savedPost.id))
    }
}