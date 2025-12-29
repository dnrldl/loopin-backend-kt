package com.loopin.api.loopinbackend.common.web

import com.loopin.api.loopinbackend.common.annotation.AuthRequirement
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
class AuthUserIdArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AuthUserId::class.java) &&
                (parameter.parameterType == Long::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val anno = parameter.getParameterAnnotation(AuthUserId::class.java)!!
        val auth = SecurityContextHolder.getContext().authentication
        if (auth == null || !auth.isAuthenticated) throw Exception()

        val userId: Long? = when (val principal = auth.principal) {
            is CustomUserDetails -> principal.userId
            else -> null
        }

        // 인증 필수인데 없으면 예외
        if (anno.requirement == AuthRequirement.REQUIRED && userId == null) throw BaseException(ErrorCode.UNAUTHORIZED)

        return userId
    }
}