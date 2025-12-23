package com.loopin.api.loopinbackend.domain.user.service

import com.loopin.api.loopinbackend.domain.user.dto.command.RegisterUserRequest
import com.loopin.api.loopinbackend.domain.user.dto.command.UpdateUserPasswordRequest
import com.loopin.api.loopinbackend.domain.user.entity.User
import com.loopin.api.loopinbackend.domain.user.repository.UserRepository
import com.loopin.api.loopinbackend.domain.user.type.Role
import com.loopin.api.loopinbackend.domain.user.type.UserStatus
import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import com.loopin.api.loopinbackend.common.error.exception.BaseException
import com.loopin.api.loopinbackend.common.error.exception.BusinessException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun getUserById(userId: Long): User =
        userRepository.findUserById(userId) ?: throw BaseException(ErrorCode.USER_NOT_FOUND)

    @Transactional
    fun register(request: RegisterUserRequest): Long {
        validateRegister(request)

        val user = User(
            email = request.email,
            password = passwordEncoder.encode(request.password)!!,
            firstName = request.firstName,
            lastName = request.lastName,
            nickname = request.nickname,
            phoneNumber = request.phoneNumber,
            bio = request.bio ?: "",
            role = Role.USER,
            status = UserStatus.ACTIVE,
            emailVerified = false,
            lastLoginAt = null,
            birthDt = request.birthDt,
        )

        val savedUser = userRepository.save(user)

        return checkNotNull(savedUser.id)
    }

    @Transactional
    fun updatePassword(userId: Long, body: UpdateUserPasswordRequest) {
        val user = userRepository.findUserById(userId) ?: throw BaseException(ErrorCode.USER_NOT_FOUND)

        if (!user.matchesPassword(body.oldPassword, passwordEncoder::matches))
            throw BaseException(ErrorCode.INVALID_OLD_PASSWORD)
        if (user.matchesPassword(body.newPassword, passwordEncoder::matches))
            throw BaseException(ErrorCode.SAME_PASSWORD)

        user.setPassword(passwordEncoder.encode(body.newPassword)!!)
    }

    @Transactional
    fun withdraw(userId: Long) {
        val user = userRepository.findUserById(userId) ?: throw BaseException(ErrorCode.USER_NOT_FOUND)
        userRepository.delete(user)
    }

    fun existsEmail(email: String): Boolean = userRepository.existsByEmail(email)
    fun existsNickname(nickname: String): Boolean = userRepository.existsByNickname(nickname)
    fun existsPhoneNumber(phoneNumber: String): Boolean = userRepository.existsByPhoneNumber(phoneNumber)

    private fun validateRegister(request: RegisterUserRequest) {
        val fields = mutableListOf<String>()

        if (existsEmail(request.email)) fields.add("email")
        if (existsNickname(request.nickname)) fields.add("nickname")
        if (existsPhoneNumber(request.phoneNumber)) fields.add("phoneNumber")

        if (!fields.isEmpty()) throw BusinessException(ErrorCode.USED_USER_INFORMATION, fields = fields)
    }
}