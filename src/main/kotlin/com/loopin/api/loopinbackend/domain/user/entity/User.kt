package com.loopin.api.loopinbackend.domain.user.entity

import com.loopin.api.loopinbackend.domain.user.type.Role
import com.loopin.api.loopinbackend.domain.user.type.UserStatus
import com.loopin.api.loopinbackend.common.entity.TimeBaseEntity
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Table(
    name = "users",
    indexes = [
        Index(name = "idx_users_email", columnList = "email", unique = true)
    ]
)
@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    private var password: String,

    @Column(nullable = false, length = 30)
    val firstName: String,

    @Column(nullable = false, length = 30)
    val lastName: String,

    @Column(nullable = false, unique = true, length = 60)
    private var nickname: String,

    @Column(nullable = false, unique = true, length = 15)
    val phoneNumber: String,

    @Column(length = 30)
    private var bio: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private var status: UserStatus,

    @Column(nullable = false)
    private var emailVerified: Boolean = false,

    val lastLoginAt: LocalDateTime?,

    val birthDt: LocalDate?
) : TimeBaseEntity() {
    fun matchesPassword(
        rawPassword: String,
        matches: (raw: String, encoded: String) -> Boolean
    ): Boolean {
        return matches(rawPassword, this.password)
    }

    fun setPassword(encodedPassword: String) {
        this.password = encodedPassword
    }

    fun getNickname(): String = this.nickname

    fun setNickname(nickname: String) {
        this.nickname = nickname
    }

    fun setBio(bio: String) {
        this.bio = bio
    }

    fun getEncodedPassword(): String = this.password

    fun verifyEmail() {
        this.emailVerified = true
        this.status = UserStatus.ACTIVE
    }
}