package com.loopin.api.loopinbackend.domain.auth.controller

import com.loopin.api.loopinbackend.common.annotation.AuthUserId
import com.loopin.api.loopinbackend.common.response.SuccessResponse
import com.loopin.api.loopinbackend.common.security.util.resolveToken
import com.loopin.api.loopinbackend.domain.auth.command.dto.UserLoginCommand
import com.loopin.api.loopinbackend.domain.auth.command.dto.UserLogoutCommand
import com.loopin.api.loopinbackend.domain.auth.command.dto.UserRefreshTokenCommand
import com.loopin.api.loopinbackend.domain.auth.command.service.AuthService
import com.loopin.api.loopinbackend.domain.auth.dto.req.UserLoginRequest
import com.loopin.api.loopinbackend.domain.auth.dto.res.UserLoginResponse
import com.loopin.api.loopinbackend.domain.auth.dto.res.UserRefreshTokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.web.bind.annotation.*
import java.time.Duration

@Tag(name = "Auth", description = "인증 API")
@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun register(
        @RequestBody body: UserLoginRequest,
        response: HttpServletResponse
    ): SuccessResponse<UserLoginResponse> {
        val result = authService.login(UserLoginCommand(body.email, body.password))

        val cookie = ResponseCookie.from("refreshToken", result.refreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .sameSite("Strict")
            .maxAge(Duration.ofDays(7))
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())

        return SuccessResponse.success(UserLoginResponse(result.accessToken))
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    fun register(@AuthUserId userId: Long, request: HttpServletRequest): SuccessResponse<Unit> {
        authService.logout(UserLogoutCommand(userId, resolveToken(request)))
        return SuccessResponse.success()
    }

    @Operation(summary = "토큰 재발행")
    @PostMapping("/refresh-token")
    fun refreshToken(
        @CookieValue(value = "refreshToken", defaultValue = "") refreshToken: String,
        response: HttpServletResponse
    ): SuccessResponse<UserRefreshTokenResponse> {
        val result = authService.refreshToken(UserRefreshTokenCommand(refreshToken))

        val cookie = ResponseCookie.from("refreshToken", result.refreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .sameSite("Strict")
            .maxAge(Duration.ofDays(7))
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())

        return SuccessResponse.success(UserRefreshTokenResponse(result.accessToken))
    }
}