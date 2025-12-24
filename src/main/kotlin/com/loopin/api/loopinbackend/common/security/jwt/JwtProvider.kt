package com.loopin.api.loopinbackend.common.security.jwt

import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import com.loopin.api.loopinbackend.domain.auth.type.TokenStatus
import com.loopin.api.loopinbackend.domain.user.type.Role
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties
) {

    fun generateAccessToken(
        userId: Long,
        email: String,
        role: Role
    ): String =
        generateToken(userId, email, role, jwtProperties.accessTokenValidity)

    fun generateRefreshToken(
        userId: Long,
        email: String,
        role: Role
    ): String =
        generateToken(userId, email, role, jwtProperties.refreshTokenValidity)

    private fun generateToken(
        userId: Long,
        email: String,
        role: Role,
        validity: Long
    ): String {
        val claims = Jwts.claims().apply {
            this["userId"] = userId
            this["role"] = role.name
        }

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(email)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + validity))
            .signWith(getSignKey())
            .compact()
    }

    private fun parseClaims(token: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            e.claims
        } catch (e: JwtException) {
            throw IllegalArgumentException(ErrorCode.INVALID_JWT.message, e)
        }
    }

    fun extractUserId(token: String): Long =
        (parseClaims(token)["userId"] as? Number)?.toLong()
            ?: throw IllegalStateException("토큰에 User Id가 포함되어있지 않습니다.")

    fun extractUsername(token: String): String =
        parseClaims(token).subject
            ?: throw IllegalStateException("토큰에 Subject가 포함되어있지 않습니다.")

    fun extractExpiredTime(token: String): Long =
        parseClaims(token).expiration?.time
            ?: throw IllegalStateException("토큰에 expiration이 포함되어있지 않습니다.")

    fun extractRole(token: String): Role =
        parseClaims(token)["role"]
            ?.toString()
            ?.let { Role.valueOf(it) }
            ?: throw IllegalStateException("JWT does not contain role")


    fun isValidToken(token: String): Boolean = getTokenStatus(token) == TokenStatus.VALID

    fun getTokenStatus(token: String?): TokenStatus {
        if (token.isNullOrEmpty()) return TokenStatus.EMPTY

        try {
            Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
            return TokenStatus.VALID
        } catch (e: ExpiredJwtException) {
            return TokenStatus.EXPIRED
        } catch (e: MalformedJwtException) {
            return TokenStatus.INVALID
        } catch (e: UnsupportedJwtException) {
            return TokenStatus.INVALID
        } catch (e: IllegalArgumentException) {
            return TokenStatus.INVALID
        } catch (e: Exception) {
            return TokenStatus.ERROR
        }
    }

    private fun getSignKey(): Key =
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.key))
}
