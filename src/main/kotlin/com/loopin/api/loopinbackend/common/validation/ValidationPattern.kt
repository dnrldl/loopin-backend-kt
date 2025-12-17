package com.loopin.api.loopinbackend.common.validation

object ValidationPattern {
    const val EMAIL_VALID: String = "^[\\w-.]+@[\\w-]+\\.[A-Za-z]{2,}$"
    const val PASSWORD_VALID: String = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).+$"
    const val PHONE_NUMBER_VALID: String = "전화번호는 10~11자리 숫자여야 합니다."
}