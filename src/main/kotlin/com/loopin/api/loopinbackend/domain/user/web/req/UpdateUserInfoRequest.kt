package com.loopin.api.loopinbackend.domain.user.web.req

import com.loopin.api.loopinbackend.common.validation.ValidationMessage
import com.loopin.api.loopinbackend.common.validation.ValidationPattern
import jakarta.validation.constraints.Size

data class UpdateUserInfoRequest(
    @field:Size(
        min = ValidationPattern.NICKNAME_MIN_LENGTH,
        max = ValidationPattern.NICKNAME_MAX_LENGTH,
        message = ValidationMessage.NICKNAME_SIZE
    )
    val nickname: String?,

    @field:Size(max = ValidationPattern.BIO_MAX_LENGTH, message = ValidationMessage.BIO_SIZE)
    val bio: String?,
)