package com.loopin.api.loopinbackend.common.security

import com.loopin.api.loopinbackend.domain.user.type.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    val userId: Long,
    private val email: String,
    private val encodedPassword: String,
    private val role: Role
) : UserDetails {
    override fun getUsername(): String = this.email

    override fun getPassword(): String = encodedPassword

    fun getRole(): Role = role

    override fun getAuthorities(): Collection<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_${role.name}"))

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}