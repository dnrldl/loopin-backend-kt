package com.loopin.api.loopinbackend.domain.user.controller

import com.loopin.api.loopinbackend.common.annotation.AuthUserId
import com.loopin.api.loopinbackend.common.response.SuccessResponse
import com.loopin.api.loopinbackend.common.response.code.SuccessCode
import com.loopin.api.loopinbackend.domain.user.command.UpdateUserInfoCommand
import com.loopin.api.loopinbackend.domain.user.command.UpdateUserPasswordCommand
import com.loopin.api.loopinbackend.domain.user.service.UserCommandService
import com.loopin.api.loopinbackend.domain.user.web.req.RegisterUserRequest
import com.loopin.api.loopinbackend.domain.user.web.req.UpdateUserInfoRequest
import com.loopin.api.loopinbackend.domain.user.web.req.UpdateUserPasswordRequest
import com.loopin.api.loopinbackend.domain.user.mapper.toInfoView
import com.loopin.api.loopinbackend.domain.user.query.CheckUserAvailableResult
import com.loopin.api.loopinbackend.domain.user.query.UserInfoView
import com.loopin.api.loopinbackend.domain.user.service.UserQueryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@Tag(name = "User", description = "유저 API")
@RestController
@RequestMapping("/api/users")
class UserController(
    private val userQueryService: UserQueryService,
    private val userCommandService: UserCommandService
) {
    @Operation(summary = "회원가입")
    @PostMapping("/register")
    fun register(@RequestBody @Valid request: RegisterUserRequest): SuccessResponse<Long> =
        SuccessResponse.of(userCommandService.register(request), SuccessCode.SAVE_SUCCESS)

    @Operation(summary = "유저 정보 조회")
    @GetMapping("/{id}")
    fun getUserInfo(@PathVariable id: Long): SuccessResponse<UserInfoView> =
        SuccessResponse.of(userQueryService.getUserById(id).toInfoView(), SuccessCode.RETRIEVE_SUCCESS)

    @Operation(summary = "이메일 유효성 조회")
    @GetMapping("/check/email")
    fun checkEmail(@RequestParam email: String): SuccessResponse<CheckUserAvailableResult> =
        SuccessResponse.of(
            CheckUserAvailableResult(userCommandService.existsByEmail(email)),
            SuccessCode.RETRIEVE_SUCCESS
        )

    @Operation(summary = "닉네임 유효성 조회")
    @GetMapping("/check/nickname")
    fun checkNickname(@RequestParam nickname: String): SuccessResponse<CheckUserAvailableResult> =
        SuccessResponse.of(
            CheckUserAvailableResult(userCommandService.existsByNickname(nickname)),
            SuccessCode.RETRIEVE_SUCCESS
        )

    @Operation(summary = "전화번호 유효성 조회")
    @GetMapping("/check/phone-number")
    fun checkPhoneNumber(@RequestParam phoneNumber: String): SuccessResponse<CheckUserAvailableResult> =
        SuccessResponse.of(
            CheckUserAvailableResult(userCommandService.existsByPhoneNumber(phoneNumber)),
            SuccessCode.RETRIEVE_SUCCESS
        )

    @Operation(summary = "비밀번호 변경")
    @PutMapping("/update-password")
    fun updatePassword(
        @AuthUserId userId: Long,
        @RequestBody request: UpdateUserPasswordRequest
    ): SuccessResponse<Unit> {
        userCommandService.updatePassword(
            UpdateUserPasswordCommand(
                oldPassword = request.oldPassword,
                newPassword = request.newPassword,
                userId = userId
            )
        )
        return SuccessResponse.of(SuccessCode.UPDATE_SUCCESS)
    }

    @Operation(summary = "유저 정보 변경")
    @PutMapping("/update")
    fun updateUserInfo(
        @AuthUserId userId: Long,
        @RequestBody request: UpdateUserInfoRequest
    ): SuccessResponse<Unit> {
        userCommandService.updateUserInfo(
            UpdateUserInfoCommand(
                userId = userId,
                nickname = request.nickname,
                bio = request.bio
            )
        )
        return SuccessResponse.of(SuccessCode.UPDATE_SUCCESS)
    }

    @Operation(summary = "회원탈퇴")
    @DeleteMapping
    fun withdraw(@AuthUserId userId: Long): SuccessResponse<Unit> {
        userCommandService.withdraw(userId)
        return SuccessResponse.of(SuccessCode.DELETE_SUCCESS)
    }
}