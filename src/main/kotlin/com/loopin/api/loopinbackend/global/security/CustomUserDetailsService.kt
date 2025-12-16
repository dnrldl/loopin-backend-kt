package com.loopin.api.loopinbackend.global.security

import com.loopin.api.loopinbackend.domain.user.repository.UserRepository
import com.loopin.api.loopinbackend.global.error.code.ErrorCode
import com.loopin.api.loopinbackend.global.error.exception.BizException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw BizException(ErrorCode.UNAUTHORIZED)
        return CustomUserDetails(user)
    }
}