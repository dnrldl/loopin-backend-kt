package com.loopin.api.loopinbackend.domain.auth.entity

import com.loopin.api.loopinbackend.common.entity.TimeBaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "refresh_token")
@Entity
class RefreshToken(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val tokenValue: String,

    @Column(nullable = false)
    val expiresAt: LocalDateTime,
) : TimeBaseEntity()