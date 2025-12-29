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

    fun getUserById(userId: Long): User =
        userJpaRepository.findUserById(userId) ?: throw BaseException(ErrorCode.USER_NOT_FOUND)

    fun existsByEmail(email: String): Boolean = userJpaRepository.existsByEmail(email)
    fun existsByNickname(nickname: String): Boolean = userJpaRepository.existsByNickname(nickname)
    fun existsByPhoneNumber(phoneNumber: String): Boolean = userJpaRepository.existsByPhoneNumber(phoneNumber)

    fun checkMyPassword(command: CheckMyPasswordCommand): Boolean {
        val user = userJpaRepository.findUserById(command.userId) ?: throw BaseException(ErrorCode.USER_NOT_FOUND)
        return passwordEncoder.matches(command.password, user.getEncodedPassword())
    }
}