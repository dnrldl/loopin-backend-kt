package com.loopin.api.loopinbackend.domain.user.service

import com.loopin.api.loopinbackend.domain.user.dto.req.UserRegisterRequest
import com.loopin.api.loopinbackend.domain.user.dto.req.UserUpdatePasswordRequest
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
    fun register(request: UserRegisterRequest): Long {
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
    fun updatePassword(userId: Long, body: UserUpdatePasswordRequest) {
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

    fun checkEmail(email: String): Boolean = userRepository.existsByEmail(email)
    fun checkNickname(nickname: String): Boolean = userRepository.existsByNickname(nickname)
    fun checkPhoneNumber(phoneNumber: String): Boolean = userRepository.existsByPhoneNumber(phoneNumber)

    private fun validateRegister(request: UserRegisterRequest) {
        val fields = mutableListOf<Map<String, List<String>>>()

        if (checkEmail(request.email)) fields.add(mapOf("email" to listOf(request.email)))
        if (checkNickname(request.nickname)) fields.add(mapOf("nickname" to listOf(request.nickname)))
        if (checkPhoneNumber(request.phoneNumber)) fields.add(mapOf("phoneNumber" to listOf(request.phoneNumber)))

        if (!fields.isEmpty()) throw BusinessException(ErrorCode.INVALID_INPUT_VALUE)
    }
}