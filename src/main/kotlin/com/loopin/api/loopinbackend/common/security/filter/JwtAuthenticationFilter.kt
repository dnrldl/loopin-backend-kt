package com.loopin.api.loopinbackend.common.security.filter

import com.loopin.api.loopinbackend.common.error.exception.BusinessException
import com.loopin.api.loopinbackend.common.redis.constant.RedisPrefix
import com.loopin.api.loopinbackend.common.redis.generator.RedisKeyGenerator
import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import com.loopin.api.loopinbackend.common.security.CustomUserDetails
import com.loopin.api.loopinbackend.common.security.CustomUserDetailsService
import com.loopin.api.loopinbackend.common.security.jwt.JwtProvider
import com.loopin.api.loopinbackend.common.security.util.resolveToken
import com.loopin.api.loopinbackend.domain.auth.mapper.toErrorCode
import com.loopin.api.loopinbackend.domain.auth.type.TokenStatus
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val redisTemplate: RedisTemplate<String, String>,
    private val jwtProvider: JwtProvider,
    private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = resolveToken(request)
        val tokenStatus = jwtProvider.getTokenStatus(token)
        if (tokenStatus != TokenStatus.VALID) throw BusinessException(tokenStatus.toErrorCode())
        if (redisTemplate.hasKey(RedisKeyGenerator.generateRedisKey(
                    RedisPrefix.BLACKLIST,
                    token!!))) throw BusinessException(ErrorCode.BLACKLISTED_TOKEN)

        val email = jwtProvider.extractUsername(token)
        val userDetails = customUserDetailsService.loadUserByUsername(email) as CustomUserDetails

        val auth = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        auth.details = WebAuthenticationDetailsSource().buildDetails(request) // 세션, IP 등등

        SecurityContextHolder.getContext().authentication = auth

        filterChain.doFilter(request, response)
    }
}