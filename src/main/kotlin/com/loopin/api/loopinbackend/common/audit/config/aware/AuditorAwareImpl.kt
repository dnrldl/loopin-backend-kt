package com.loopin.api.loopinbackend.common.audit.config.aware

import com.loopin.api.loopinbackend.common.security.CustomUserDetails
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuditorAwareImpl : AuditorAware<Long> {
    override fun getCurrentAuditor(): Optional<Long> {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: return Optional.empty()

        if (!authentication.isAuthenticated) {
            return Optional.empty()
        }

        val principal = authentication.principal
        if (principal !is CustomUserDetails) {
            return Optional.empty()
        }

        return Optional.of(principal.userId)
    }
}