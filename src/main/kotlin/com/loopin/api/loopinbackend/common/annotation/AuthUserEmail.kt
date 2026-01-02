package com.loopin.api.loopinbackend.common.annotation

/**
 * 인증된 사용자 email 추출
 */
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class AuthUserEmail(
    val requirement: AuthRequirement = AuthRequirement.REQUIRED
)
