package com.loopin.api.loopinbackend.domain.user.controller

import com.loopin.api.loopinbackend.domain.user.dto.req.UserRegisterRequest
import com.loopin.api.loopinbackend.domain.user.dto.req.UserUpdatePasswordRequest
import com.loopin.api.loopinbackend.domain.user.dto.res.UserCheckResponse
import com.loopin.api.loopinbackend.domain.user.dto.res.UserInfoResponse
import com.loopin.api.loopinbackend.domain.user.mapper.toInfoResponse
import com.loopin.api.loopinbackend.domain.user.service.UserService
import com.loopin.api.loopinbackend.global.annotation.AuthUserId
import com.loopin.api.loopinbackend.global.response.CommonResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "User", description = "유저 API")
@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @Operation(summary = "회원가입")
    @PostMapping("/register")
    fun register(@RequestBody request: UserRegisterRequest): CommonResponse<Long> =
        CommonResponse.success(userService.register(request))

    @Operation(summary = "유저 정보 조회")
    @GetMapping("/{id}")
    fun getUserInfo(@PathVariable id: Long): CommonResponse<UserInfoResponse> =
        CommonResponse.success(userService.getUserById(id).toInfoResponse())

    @Operation(summary = "이메일 유효성 조회")
    @GetMapping("/check/email")
    fun checkEmail(@RequestParam email: String): CommonResponse<UserCheckResponse> =
        CommonResponse.success(UserCheckResponse(userService.checkEmail(email)))

    @Operation(summary = "닉네임 유효성 조회")
    @GetMapping("/check/nickname")
    fun checkNickname(@RequestParam nickname: String): CommonResponse<UserCheckResponse> =
        CommonResponse.success(UserCheckResponse(userService.checkNickname(nickname)))

    @Operation(summary = "전화번호 유효성 조회")
    @GetMapping("/check/phone-number")
    fun checkPhoneNumber(@RequestParam phoneNumber: String): CommonResponse<UserCheckResponse> =
        CommonResponse.success(UserCheckResponse(userService.checkPhoneNumber(phoneNumber)))

    @Operation(summary = "비밀번호 변경")
    @PatchMapping("/password")
    fun updatePassword(@AuthUserId userId: Long, @RequestBody body: UserUpdatePasswordRequest): ResponseEntity<Void> {
        userService.updatePassword(userId, body)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "회원탈퇴")
    @DeleteMapping
    fun withdraw(@AuthUserId userId: Long): ResponseEntity<Void> {
        userService.withdraw(userId)
        return ResponseEntity.noContent().build()
    }
}