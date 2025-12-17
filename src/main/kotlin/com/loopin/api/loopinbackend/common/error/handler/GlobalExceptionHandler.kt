package com.loopin.api.loopinbackend.common.error.handler

import com.loopin.api.loopinbackend.common.error.code.ErrorCode
import com.loopin.api.loopinbackend.common.error.exception.BaseException
import com.loopin.api.loopinbackend.common.response.CommonResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(BaseException::class, AuthenticationException::class)
    private fun handleBizException(e: BaseException, request: HttpServletRequest): ResponseEntity<CommonResponse<Void>> {
        val errorCode = e.errorCode
        printErrorLog(errorCode, request, e)

        return ResponseEntity.status(errorCode.status)
            .body(CommonResponse.fail(errorCode))
    }

    @ExceptionHandler(Exception::class)
    private fun handleException(e: Exception, request: HttpServletRequest): ResponseEntity<CommonResponse<Void>> {
        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR
        printErrorLog(errorCode, request, e)

        return ResponseEntity.status(errorCode.status)
            .body(CommonResponse.fail(errorCode))
    }

    private fun printErrorLog(errorCode: ErrorCode, request: HttpServletRequest, e: Exception?) {
        when (e) {
            is BaseException -> {
                log.warn("[{}] {}: {} - {} {}", errorCode.code, errorCode.status,
                errorCode.message, request.method, request.requestURI)
            } else -> {
                log.error("[{}] {}: {} - {} {}", errorCode.code, errorCode.status,
                errorCode.message, request.method, request.requestURI, e)
            }
        }
    }
}