package com.loopin.api.loopinbackend.domain.post.controller

import com.loopin.api.loopinbackend.common.annotation.AuthUserId
import com.loopin.api.loopinbackend.common.response.SuccessResponse
import com.loopin.api.loopinbackend.common.response.code.SuccessCode
import com.loopin.api.loopinbackend.domain.post.dto.req.CreatePostRequest
import com.loopin.api.loopinbackend.domain.post.command.dto.result.CreatePostResult
import com.loopin.api.loopinbackend.domain.post.command.service.PostCommandService
import com.loopin.api.loopinbackend.domain.post.query.dto.PostDetailView
import com.loopin.api.loopinbackend.domain.post.query.service.PostQueryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Post", description = "게시글 API")
@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postQueryService: PostQueryService,
    private val postCommandService: PostCommandService
) {
    @Operation(summary = "게시글 등록")
    @PostMapping("/create")
    fun createPost(@AuthUserId userId: Long, @RequestBody @Valid request: CreatePostRequest): SuccessResponse<CreatePostResult> =
        SuccessResponse.of(postCommandService.createPost(request, userId), SuccessCode.SAVE_SUCCESS)

    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/{postId}")
    fun createPost(@AuthUserId userId: Long?, @PathVariable postId: Long): SuccessResponse<PostDetailView> =
        SuccessResponse.of(postQueryService.getPostDetail(postId, userId), SuccessCode.RETRIEVE_SUCCESS)
}