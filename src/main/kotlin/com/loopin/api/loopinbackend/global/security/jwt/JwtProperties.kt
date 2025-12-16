package com.loopin.api.loopinbackend.global.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val key: String,
    val accessTokenValidity: Long,
    val refreshTokenValidity: Long
)
