package com.loopin.api.loopinbackend.common.web

import com.loopin.api.loopinbackend.common.annotation.AuthRequirement
import com.loopin.api.loopinbackend.common.annotation.AuthUserEmail
import com.loopin.api.loopinbackend.common.annotation.AuthUserId
import com.loopin.api.loopinbackend.common.error.exception.BaseException
import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import com.loopin.api.loopinbackend.common.security.CustomUserDetails
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AuthPrincipalArgumentResolver : HandlerMethodArgumentResolver {

    // 처리할 파라미터 판별
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return when {
            // @AuthUserId
            parameter.hasParameterAnnotation(AuthUserId::class.java) ->
                parameter.parameterType == Long::class.javaPrimitiveType ||
                        parameter.parameterType == Long::class.javaObjectType

            // @AuthUserEmail
            parameter.hasParameterAnnotation(AuthUserEmail::class.java) ->
                parameter.parameterType == String::class.java

            else -> false
        }
    }

    // 실제 값 부여
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val auth = SecurityContextHolder.getContext().authentication

        val principal: CustomUserDetails? =
            if (auth != null && auth.isAuthenticated) {
                auth.principal as? CustomUserDetails
            } else {
                null
            }

        // 어떤 annotation인지 판별
        return when {
            parameter.hasParameterAnnotation(AuthUserId::class.java) -> {
                val anno = parameter.getParameterAnnotation(AuthUserId::class.java)!!
                val userId = principal?.userId

                validateRequirement(anno.requirement, userId, parameter, "@AuthUserId")
                userId
            }

            parameter.hasParameterAnnotation(AuthUserEmail::class.java) -> {
                val anno = parameter.getParameterAnnotation(AuthUserEmail::class.java)!!
                val email = principal?.username

                validateRequirement(anno.requirement, email, parameter, "@AuthUserEmail")
                email
            }

            else -> null
        }
    }

    private fun validateRequirement(
        requirement: AuthRequirement,
        value: Any?,
        parameter: MethodParameter,
        annoName: String
    ) {
        // OPTIONAL인데 non-null 타입이면 설계 오류
        if (requirement == AuthRequirement.OPTIONAL && parameter.parameterType.isPrimitive) {
            throw IllegalStateException(
                "$annoName OPTIONAL은 nullable 파라미터로 선언해야 합니다."
            )
        }

        // REQUIRED인데 값 없으면 인증 오류
        if (requirement == AuthRequirement.REQUIRED && value == null) {
            throw BaseException(ErrorCode.UNAUTHORIZED)
        }
    }
}