package com.loopin.api.loopinbackend.domain.auth.dto.req

import com.loopin.api.loopinbackend.common.validation.ValidationMessage
import com.loopin.api.loopinbackend.common.validation.ValidationPattern
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UserLoginRequest(
    @field:NotBlank(message = ValidationMessage.EMAIL_NOT_BLANK)
    @field:Email(message = ValidationMessage.EMAIL_INVALID)
    val email: String,

    @field:NotBlank(message = ValidationMessage.PASSWORD_NOT_BLANK)
    @field:Pattern(regexp = ValidationPattern.PASSWORD_VALID, message = ValidationMessage.PASSWORD_PATTERN)
    @Size(min = 8, max = 20, message = ValidationMessage.PASSWORD_SIZE)
    val password: String
)