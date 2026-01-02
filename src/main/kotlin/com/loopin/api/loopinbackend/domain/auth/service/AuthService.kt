package com.loopin.api.loopinbackend.domain.auth.service

import com.loopin.api.loopinbackend.common.error.exception.BusinessException
import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import com.loopin.api.loopinbackend.common.security.CustomUserDetails
import com.loopin.api.loopinbackend.common.security.jwt.JwtProvider
import com.loopin.api.loopinbackend.domain.auth.command.*
import com.loopin.api.loopinbackend.domain.auth.repository.RedisAuthTokenRepository
import com.loopin.api.loopinbackend.domain.user.type.Role
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val redisAuthTokenRepository: RedisAuthTokenRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtProvider: JwtProvider
) {

    fun login(command: UserLoginCommand): UserLoginResult {
        try {
            val auth = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    command.email,
                    command.password
                )
            )
            val userDetails = auth.principal as CustomUserDetails

            val accessToken = jwtProvider.generateAccessToken(userDetails.userId, userDetails.username, Role.USER)
            val refreshToken = jwtProvider.generateRefreshToken(userDetails.userId, userDetails.username, Role.USER)

            // 리프레시 토큰 저장 (Redis)
            redisAuthTokenRepository.saveRefreshToken(userDetails.userId, refreshToken)

            return UserLoginResult(accessToken, refreshToken)
        } catch (e: AuthenticationException) {
            throw BusinessException(ErrorCode.INVALID_LOGIN)
        }
    }

    fun logout(command: UserLogoutCommand) {
        // accessToken 있으면 블랙리스트 등록
        command.accessToken?.let {
            redisAuthTokenRepository.saveBlacklistToken(command.userId, it)
        }

        // refreshToken 폐기
        redisAuthTokenRepository.deleteRefreshToken(command.userId)
    }

    fun refreshToken(command: UserRefreshTokenCommand): UserRefreshTokenResult {
        val refreshToken = command.refreshToken
            .takeIf { it.isNotBlank() } ?: throw BusinessException(ErrorCode.INVALID_REFRESH_TOKEN)

        // refreshToken에서 사용자 정보 추출
        val userId = jwtProvider.extractUserId(refreshToken)
        val email = jwtProvider.extractUsername(refreshToken)

        // 레디스에 저장된 refreshToken 추출
        val savedRefreshToken = redisAuthTokenRepository.findRefreshToken(userId)

        if (savedRefreshToken != refreshToken) throw BusinessException(ErrorCode.INVALID_REFRESH_TOKEN)

        // 새 토큰 발급
        val newAccessToken = jwtProvider.generateAccessToken(userId, email, Role.USER)
        val newRefreshToken = jwtProvider.generateRefreshToken(userId, email, Role.USER)

        // 기존 refreshToken 삭제, 새로운 토큰 등록
        redisAuthTokenRepository.deleteRefreshToken(userId)
        redisAuthTokenRepository.saveRefreshToken(userId, newRefreshToken)
        return UserRefreshTokenResult(accessToken = newAccessToken, refreshToken = newRefreshToken)
    }
}