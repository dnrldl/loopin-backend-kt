package com.loopin.api.loopinbackend.common.security.jwt

import com.loopin.api.loopinbackend.domain.auth.type.TokenStatus
import com.loopin.api.loopinbackend.domain.user.type.Role
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
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
