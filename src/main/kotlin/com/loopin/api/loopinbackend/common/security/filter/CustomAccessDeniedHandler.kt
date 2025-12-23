package com.loopin.api.loopinbackend.common.security.filter

import com.loopin.api.loopinbackend.common.logging.logger
import com.loopin.api.loopinbackend.common.response.ErrorResponse
import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper

@Component
class CustomAccessDeniedHandler(
    private val objectMapper: ObjectMapper
) : AccessDeniedHandler { // 403

    val log = logger()

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        log.warn("Authentication failed(403): {}", accessDeniedException.message)

        val errorCode = ErrorCode.ACCESS_DENIED
        val errorResponse = ErrorResponse.fail(errorCode)

        response.status = errorCode.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"

        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}