package com.loopin.api.loopinbackend.domain.user.service

import com.loopin.api.loopinbackend.common.error.exception.BaseException
import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import com.loopin.api.loopinbackend.domain.user.command.CheckMyPasswordCommand
import com.loopin.api.loopinbackend.domain.user.entity.User
import com.loopin.api.loopinbackend.domain.user.repository.UserJpaRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserQueryService(
    private val userJpaRepository: UserJpaRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun getUserInfo(userId: Long): User =
        userJpaRepository.findUserById(userId) ?: throw BaseException(ErrorCode.USER_NOT_FOUND)

    fun getMyInfo(userId: Long): User = getUserInfo(userId)

    fun existsByEmail(email: String): Boolean = userJpaRepository.existsByEmail(email)
    fun existsByNickname(nickname: String): Boolean = userJpaRepository.existsByNickname(nickname)
    fun existsByPhoneNumber(phoneNumber: String): Boolean = userJpaRepository.existsByPhoneNumber(phoneNumber)

    fun checkMyPassword(userId: Long, password: String): Boolean {
        val user = userJpaRepository.findUserById(userId) ?: throw BaseException(ErrorCode.USER_NOT_FOUND)
        return passwordEncoder.matches(password, user.getEncodedPassword())
    }
}