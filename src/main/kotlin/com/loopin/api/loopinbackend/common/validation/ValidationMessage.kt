package com.loopin.api.loopinbackend.common.validation


object ValidationMessage {
    // EMAIL
    const val EMAIL_INVALID: String = "이메일 형식이 올바르지 않습니다."

    // NOT_BLANK
    const val EMAIL_NOT_BLANK: String = "이메일은 필수 입력값입니다."
    const val PASSWORD_NOT_BLANK: String = "비밀번호는 필수 입력값입니다."
    const val NICKNAME_NOT_BLANK: String = "닉네임은 필수 입력값입니다."
    const val FIRST_NAME_NOT_BLANK: String = "이름은 필수 입력값입니다."
    const val LAST_NAME_NOT_BLANK: String = "성은 필수 입력값입니다."
    const val PHONE_NUMBER_NOT_BLANK: String = "전화번호는 필수 입력값입니다."
    const val GENDER_NOT_BLANK: String = "성별는 필수 입력값입니다."
    const val BIRTH_NOT_BLANK: String = "생년월일는 필수 입력값입니다."

    // Size
    const val PASSWORD_SIZE: String = "비밀번호는 8~20자 사이여야 합니다."
    const val NICKNAME_SIZE: String = "닉네임은 2~12자여야 합니다."
    const val FIRST_NAME_SIZE: String = "이름은 최대 10자입니다."
    const val LAST_NAME_SIZE: String = "성은 최대 10자입니다."
    const val BIO_SIZE: String = "자기소개글은 최대 100자입니다."

    // Pattern
    const val PASSWORD_PATTERN: String = "비밀번호는 대문자·소문자·숫자·특수문자를 모두 포함해야 합니다."
    const val EMAIL_PATTERN: String = "이메일은 example@domain.com 형식이어야 합니다."
    const val PHONE_NUMBER_PATTERN: String = "전화번호는 10~11자리 숫자여야 합니다."

    // Email
    const val EMAIL_VALID: String = "유효한 이메일 형식이어야 합니다."

    // Past
    const val BIRTH_PAST: String = "과거의 날짜이어야 합니다."
}