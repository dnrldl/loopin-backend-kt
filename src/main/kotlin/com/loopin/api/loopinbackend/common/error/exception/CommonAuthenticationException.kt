package com.loopin.api.loopinbackend.common.error.exception

import org.springframework.security.core.AuthenticationException

class CommonAuthenticationException(
    override val message: String
) : AuthenticationException(message)