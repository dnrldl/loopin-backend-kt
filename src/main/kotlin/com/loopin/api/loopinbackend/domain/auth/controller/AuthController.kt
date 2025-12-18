package com.loopin.api.loopinbackend.domain.auth.controller

import com.loopin.api.loopinbackend.domain.auth.dto.req.UserLoginRequest
import com.loopin.api.loopinbackend.domain.auth.dto.res.UserLoginResponse
import com.loopin.api.loopinbackend.domain.auth.service.AuthService
import com.loopin.api.loopinbackend.common.annotation.AuthUserId
import com.loopin.api.loopinbackend.common.response.BaseResponse
import com.loopin.api.loopinbackend.common.response.SuccessResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth", description = "인증 API")
@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun register(@RequestBody request: UserLoginRequest): SuccessResponse<UserLoginResponse> {
        val accessToken = authService.login(request).accessToken
        return SuccessResponse.success(UserLoginResponse(accessToken))
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    fun register(@AuthUserId userId: Long): SuccessResponse<Unit> {
        authService.logout(userId)
        return SuccessResponse.success()
    }
}