package com.loopin.api.loopinbackend.common.security

import com.loopin.api.loopinbackend.domain.user.repository.UserRepository
import com.loopin.api.loopinbackend.common.error.code.ErrorCode
import com.loopin.api.loopinbackend.common.error.exception.BaseException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw BaseException(ErrorCode.UNAUTHORIZED)
        return CustomUserDetails(user)
    }
}