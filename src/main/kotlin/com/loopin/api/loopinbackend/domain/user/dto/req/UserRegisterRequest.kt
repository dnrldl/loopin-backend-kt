package com.loopin.api.loopinbackend.domain.user.dto.req

import com.loopin.api.loopinbackend.common.validation.ValidationMessage
import com.loopin.api.loopinbackend.common.validation.ValidationPattern
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class UserRegisterRequest(

    @field:NotBlank(message = ValidationMessage.EMAIL_NOT_BLANK)
    @field:Email(message = ValidationMessage.EMAIL_INVALID)
    val email: String,

    @field:NotBlank(message = ValidationMessage.PASSWORD_NOT_BLANK)
    @field:Pattern(regexp = ValidationPattern.PASSWORD_VALID, message = ValidationMessage.PASSWORD_PATTERN)
    @Size(
        min = ValidationPattern.PASSWORD_MIN_LENGTH,
        max = ValidationPattern.PASSWORD_MAX_LENGTH,
        message = ValidationMessage.PASSWORD_SIZE
    )
    val password: String,

    @field:NotBlank(message = ValidationMessage.FIRST_NAME_NOT_BLANK)
    val firstName: String,

    @field:NotBlank(message = ValidationMessage.LAST_NAME_NOT_BLANK)
    val lastName: String,

    @field:NotBlank(message = ValidationMessage.NICKNAME_NOT_BLANK)
    @Size(
        min = ValidationPattern.NICKNAME_MIN_LENGTH,
        max = ValidationPattern.NICKNAME_MAX_LENGTH,
        message = ValidationMessage.NICKNAME_SIZE
    )
    val nickname: String,

    @field:NotBlank(message = "전화번호는 필수 입력값입니다.")
    @field:Pattern(
        regexp = "^01[016789]\\d{7,8}$",
        message = "전화번호는 10~11자리 숫자 휴대폰 번호여야 합니다."
    )
    val phoneNumber: String,

    @Size(max = ValidationPattern.BIO_MAX_LENGTH, message = ValidationMessage.BIO_SIZE)
    val bio: String?,

    @field:Past(message = ValidationMessage.BIRTH_PAST)
    val birthDt: LocalDate?
)
