package com.loopin.api.loopinbackend.domain.user.controller

import com.loopin.api.loopinbackend.common.annotation.AuthUserId
import com.loopin.api.loopinbackend.common.response.SuccessResponse
import com.loopin.api.loopinbackend.common.response.code.SuccessCode
import com.loopin.api.loopinbackend.domain.user.dto.command.RegisterUserRequest
import com.loopin.api.loopinbackend.domain.user.dto.command.UpdateUserPasswordRequest
import com.loopin.api.loopinbackend.domain.user.dto.result.CheckUserAvailableResult
import com.loopin.api.loopinbackend.domain.user.dto.view.UserInfoView
import com.loopin.api.loopinbackend.domain.user.mapper.toInfoView
import com.loopin.api.loopinbackend.domain.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@Tag(name = "User", description = "유저 API")
@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @Operation(summary = "회원가입")
    @PostMapping("/register")
    fun register(@RequestBody @Valid request: RegisterUserRequest): SuccessResponse<Long> =
        SuccessResponse.of(userService.register(request), SuccessCode.SAVE_SUCCESS)

    @Operation(summary = "유저 정보 조회")
    @GetMapping("/{id}")
    fun getUserInfo(@PathVariable id: Long): SuccessResponse<UserInfoView> =
        SuccessResponse.of(userService.getUserById(id).toInfoView(), SuccessCode.RETRIEVE_SUCCESS)

    @Operation(summary = "이메일 유효성 조회")
    @GetMapping("/check/email")
    fun checkEmail(@RequestParam email: String): SuccessResponse<CheckUserAvailableResult> =
        SuccessResponse.of(CheckUserAvailableResult(userService.existsEmail(email)), SuccessCode.RETRIEVE_SUCCESS)

    @Operation(summary = "닉네임 유효성 조회")
    @GetMapping("/check/nickname")
    fun checkNickname(@RequestParam nickname: String): SuccessResponse<CheckUserAvailableResult> =
        SuccessResponse.of(CheckUserAvailableResult(userService.existsNickname(nickname)), SuccessCode.RETRIEVE_SUCCESS)

    @Operation(summary = "전화번호 유효성 조회")
    @GetMapping("/check/phone-number")
    fun checkPhoneNumber(@RequestParam phoneNumber: String): SuccessResponse<CheckUserAvailableResult> =
        SuccessResponse.of(CheckUserAvailableResult(userService.existsPhoneNumber(phoneNumber)), SuccessCode.RETRIEVE_SUCCESS)

    @Operation(summary = "비밀번호 변경")
    @PatchMapping("/password")
    fun updatePassword(@AuthUserId userId: Long, @RequestBody body: UpdateUserPasswordRequest): SuccessResponse<Unit> {
        userService.updatePassword(userId, body)
        return SuccessResponse.of(SuccessCode.UPDATE_SUCCESS)
    }

    @Operation(summary = "회원탈퇴")
    @DeleteMapping
    fun withdraw(@AuthUserId userId: Long): SuccessResponse<Unit> {
        userService.withdraw(userId)
        return SuccessResponse.of(SuccessCode.DELETE_SUCCESS)
    }
}