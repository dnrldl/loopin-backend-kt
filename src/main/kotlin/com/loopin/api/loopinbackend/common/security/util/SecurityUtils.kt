package com.loopin.api.loopinbackend.common.security.util

import com.loopin.api.loopinbackend.domain.user.entity.User
import com.loopin.api.loopinbackend.common.security.CustomUserDetails
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

fun getCurrentAuth(): Authentication {
    val auth = SecurityContextHolder.getContext().authentication
    if (auth == null || !auth.isAuthenticated) throw Exception()
    return auth
}

fun getCurrentUser(): User {
    val auth = getCurrentAuth()
    val principal = auth.principal
    if (principal !is CustomUserDetails) throw Exception()

    return principal.user
}

fun resolveToken(request: HttpServletRequest): String? {
    val bearerToken = request.getHeader("Authorization") ?: return null;

    if (!bearerToken.startsWith("Bearer ")) return null

    return bearerToken.substring(7)
}