package com.loopin.api.loopinbackend.domain.user.dto.req

import com.loopin.api.loopinbackend.common.validation.ValidationMessage
import com.loopin.api.loopinbackend.common.validation.ValidationPattern
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UserUpdatePasswordRequest(
    @field:NotBlank(message = ValidationMessage.PASSWORD_NOT_BLANK)
    @field:Pattern(regexp = ValidationPattern.PASSWORD_VALID, message = ValidationMessage.PASSWORD_PATTERN)
    @Size(
        min = ValidationPattern.PASSWORD_MIN_LENGTH,
        max = ValidationPattern.PASSWORD_MAX_LENGTH,
        message = ValidationMessage.PASSWORD_SIZE
    )
    val oldPassword: String,

    @field:NotBlank(message = ValidationMessage.PASSWORD_NOT_BLANK)
    @field:Pattern(regexp = ValidationPattern.PASSWORD_VALID, message = ValidationMessage.PASSWORD_PATTERN)
    @Size(
        min = ValidationPattern.PASSWORD_MIN_LENGTH,
        max = ValidationPattern.PASSWORD_MAX_LENGTH,
        message = ValidationMessage.PASSWORD_SIZE
    )
    val newPassword: String
)
