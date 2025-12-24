package com.loopin.api.loopinbackend.domain.user.repository

import com.loopin.api.loopinbackend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, Long> {
    fun findUserById(id: Long): User?
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
    fun existsByNickname(nickname: String): Boolean
    fun existsByPhoneNumber(phoneNumber: String): Boolean
}