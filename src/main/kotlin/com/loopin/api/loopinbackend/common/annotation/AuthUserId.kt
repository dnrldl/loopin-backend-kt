package com.loopin.api.loopinbackend.common.annotation

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class AuthUserId(
    val requirement: AuthRequirement = AuthRequirement.REQUIRED
)

enum class AuthRequirement {
    REQUIRED,
    OPTIONAL
}