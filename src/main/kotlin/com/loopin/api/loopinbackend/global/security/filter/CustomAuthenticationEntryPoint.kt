package com.loopin.api.loopinbackend.global.security.filter

import com.loopin.api.loopinbackend.global.error.code.ErrorCode
import com.loopin.api.loopinbackend.global.response.CommonErrorResponse
import com.loopin.api.loopinbackend.global.response.CommonResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
): AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val errorCode = ErrorCode.UNAUTHORIZED
        val errorResponse = CommonResponse.fail(errorCode)

        response.status = errorCode.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse))
    }
}