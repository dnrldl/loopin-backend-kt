package com.loopin.api.loopinbackend.common.security

import com.loopin.api.loopinbackend.domain.user.repository.UserJpaRepository
import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import com.loopin.api.loopinbackend.common.error.exception.BaseException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userJpaRepository: UserJpaRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userJpaRepository.findByEmail(email) ?: throw UsernameNotFoundException("User not found: $email")
        return CustomUserDetails(user)
    }
}