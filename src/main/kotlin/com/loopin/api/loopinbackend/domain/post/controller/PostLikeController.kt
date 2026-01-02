package com.loopin.api.loopinbackend.domain.post.controller

import com.loopin.api.loopinbackend.common.annotation.AuthUserId
import com.loopin.api.loopinbackend.common.response.SuccessResponse
import com.loopin.api.loopinbackend.domain.post.command.LikePostCommand
import com.loopin.api.loopinbackend.domain.post.service.PostLikeCommandService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "PostLike", description = "게시글 API")
@RestController
@RequestMapping("/api/posts")
class PostLikeController(
    private val postLikeCommandService: PostLikeCommandService
) {

    @Operation(summary = "게시글 등록")
    @PostMapping("/{postId}/like")
    fun likePost(@AuthUserId userId: Long, @PathVariable postId: Long): SuccessResponse<Void> {
        postLikeCommandService.likePost(LikePostCommand(postId, userId))
    }
}