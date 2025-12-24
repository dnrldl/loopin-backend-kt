package com.loopin.api.loopinbackend.domain.user.query.service

import com.loopin.api.loopinbackend.common.error.exception.BaseException
import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import com.loopin.api.loopinbackend.domain.user.entity.User
import com.loopin.api.loopinbackend.domain.user.repository.UserJpaRepository
import org.springframework.stereotype.Service

@Service
class UserQueryService(
    private val userJpaRepository: UserJpaRepository,
) {

    fun getUserById(userId: Long): User =
        userJpaRepository.findUserById(userId) ?: throw BaseException(ErrorCode.USER_NOT_FOUND)
}