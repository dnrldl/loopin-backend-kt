package com.loopin.api.loopinbackend.common.validation

object ValidationPattern {
    // REGEXP
    const val EMAIL_VALID: String = "^[\\w-.]+@[\\w-]+\\.[A-Za-z]{2,}$"
    const val PASSWORD_VALID: String = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).+$"
    const val PHONE_NUMBER_VALID: String = "^01[016789]\\d{7,8}$"

    // SIZE
    const val PASSWORD_MIN_LENGTH: Int = 8
    const val PASSWORD_MAX_LENGTH: Int = 20
    const val NICKNAME_MIN_LENGTH: Int = 2
    const val NICKNAME_MAX_LENGTH: Int = 12
    const val BIO_MAX_LENGTH: Int = 100
}