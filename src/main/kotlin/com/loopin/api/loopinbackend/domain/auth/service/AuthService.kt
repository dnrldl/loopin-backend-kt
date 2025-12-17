package com.loopin.api.loopinbackend.domain.auth.service

import com.loopin.api.loopinbackend.domain.auth.dto.req.UserLoginRequest
import com.loopin.api.loopinbackend.domain.auth.dto.res.AuthToken
import com.loopin.api.loopinbackend.domain.user.type.Role
import com.loopin.api.loopinbackend.common.security.CustomUserDetails
import com.loopin.api.loopinbackend.common.security.jwt.JwtProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtProvider: JwtProvider
) {

    fun login(request: UserLoginRequest): AuthToken {
        val auth = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )
        val user = (auth.principal as CustomUserDetails).user

        val accessToken = jwtProvider.generateAccessToken(user.id!!, user.email, Role.USER)
        val refreshToken = jwtProvider.generateRefreshToken(user.id!!, user.email, Role.USER)

        return AuthToken(accessToken, refreshToken)
    }

    fun logout(userId: Long) {

    }
}