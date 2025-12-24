package com.loopin.api.loopinbackend.domain.user.command.service

import com.loopin.api.loopinbackend.common.error.exception.BaseException
import com.loopin.api.loopinbackend.common.error.exception.BusinessException
import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import com.loopin.api.loopinbackend.domain.user.command.dto.UpdateUserInfoCommand
import com.loopin.api.loopinbackend.domain.user.command.dto.UpdateUserPasswordCommand
import com.loopin.api.loopinbackend.domain.user.dto.req.RegisterUserRequest
import com.loopin.api.loopinbackend.domain.user.entity.User
import com.loopin.api.loopinbackend.domain.user.repository.UserJpaRepository
import com.loopin.api.loopinbackend.domain.user.type.Role
import com.loopin.api.loopinbackend.domain.user.type.UserStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserCommandService(
    private val userJpaRepository: UserJpaRepository,
    private val passwordEncoder: PasswordEncoder
) {

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

        val savedUser = userJpaRepository.save(user)

        return checkNotNull(savedUser.id)
    }

    fun updatePassword(command: UpdateUserPasswordCommand) {
        val user = userJpaRepository.findUserById(command.userId) ?: throw BaseException(ErrorCode.USER_NOT_FOUND)

        if (!user.matchesPassword(command.oldPassword, passwordEncoder::matches))
            throw BaseException(ErrorCode.INVALID_OLD_PASSWORD)
        if (user.matchesPassword(command.newPassword, passwordEncoder::matches))
            throw BaseException(ErrorCode.SAME_PASSWORD)

        user.setPassword(passwordEncoder.encode(command.newPassword)!!)
    }

    fun updateUserInfo(command: UpdateUserInfoCommand) {
        val user = userJpaRepository.findUserById(command.userId) ?: throw BaseException(ErrorCode.USER_NOT_FOUND)

        if (command.hasNickname()) {
            if (user.getNickname() == command.nickname ||
                existsByNickname(command.nickname!!)) throw BusinessException(ErrorCode.DUPLICATED_NICKNAME)

            user.setNickname(command.nickname)
        }
        if (command.hasBio()) {
            user.setBio(command.bio!!)
        }
    }

    fun withdraw(userId: Long) {
        val user = userJpaRepository.findUserById(userId) ?: throw BaseException(ErrorCode.USER_NOT_FOUND)
        userJpaRepository.delete(user)
    }

    fun existsByEmail(email: String): Boolean = userJpaRepository.existsByEmail(email)
    fun existsByNickname(nickname: String): Boolean = userJpaRepository.existsByNickname(nickname)
    fun existsByPhoneNumber(phoneNumber: String): Boolean = userJpaRepository.existsByPhoneNumber(phoneNumber)


    private fun validateRegister(request: RegisterUserRequest) {
        val fields = mutableListOf<String>()

        if (existsByEmail(request.email)) fields.add("email")
        if (existsByNickname(request.nickname)) fields.add("nickname")
        if (existsByPhoneNumber(request.phoneNumber)) fields.add("phoneNumber")

        if (!fields.isEmpty()) throw BusinessException(ErrorCode.USED_USER_INFORMATION, fields = fields)
    }
}