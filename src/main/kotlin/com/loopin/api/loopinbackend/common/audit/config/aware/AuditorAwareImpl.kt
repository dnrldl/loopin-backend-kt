package com.loopin.api.loopinbackend.common.audit.config.aware

import com.loopin.api.loopinbackend.common.security.util.getCurrentUser
import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class AuditorAwareImpl : AuditorAware<Long> {
    override fun getCurrentAuditor(): Optional<Long> = Optional.ofNullable(getCurrentUser().id)
}