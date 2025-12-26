package com.loopin.api.loopinbackend.common.security.filter

import com.loopin.api.loopinbackend.common.error.exception.CommonAuthenticationException
import com.loopin.api.loopinbackend.common.logging.logger
import com.loopin.api.loopinbackend.common.security.CustomUserDetails
import com.loopin.api.loopinbackend.common.security.CustomUserDetailsService
import com.loopin.api.loopinbackend.common.security.jwt.JwtProvider
import com.loopin.api.loopinbackend.common.security.util.resolveToken
import com.loopin.api.loopinbackend.domain.auth.infra.redis.RedisAuthTokenRepository
import com.loopin.api.loopinbackend.domain.auth.type.TokenStatus
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
    private val customUserDetailsService: CustomUserDetailsService,
    private val redisAuthTokenRepository: RedisAuthTokenRepository
) : OncePerRequestFilter() {

    val log = logger()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = resolveToken(request)
        val tokenStatus = jwtProvider.getTokenStatus(token)

        log.debug("요청된 토큰 = {}", token)
        log.debug("요청된 토큰 상태 = {}", tokenStatus)

        if (tokenStatus != TokenStatus.EMPTY) {
            if (tokenStatus != TokenStatus.VALID) throw CommonAuthenticationException("유효하지 않은 토큰입니다. tokenStatus = $tokenStatus")
            if (redisAuthTokenRepository.hasBlacklistToken(token!!)) throw CommonAuthenticationException("로그아웃된 토큰입니다.")

            val email = jwtProvider.extractUsername(token)
            val userDetails = customUserDetailsService.loadUserByUsername(email) as CustomUserDetails

            val auth = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            auth.details = WebAuthenticationDetailsSource().buildDetails(request) // 세션, IP 등등

            SecurityContextHolder.getContext().authentication = auth
        }

        filterChain.doFilter(request, response)
    }
}