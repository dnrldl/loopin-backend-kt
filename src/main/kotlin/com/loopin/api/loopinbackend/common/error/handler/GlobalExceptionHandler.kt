package com.loopin.api.loopinbackend.common.error.handler

import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import com.loopin.api.loopinbackend.common.error.exception.BaseException
import com.loopin.api.loopinbackend.common.response.BaseResponse
import com.loopin.api.loopinbackend.common.response.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleRequestBodyValidation(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.INVALID_INPUT_VALUE
        printErrorLog(errorCode, request, e)

        val error = e.bindingResult.fieldErrors
            .groupBy { it.field }
            .mapValues { (_, errors) ->
                errors.mapNotNull { it.defaultMessage }
            }

        return ResponseEntity.status(errorCode.status)
            .body(ErrorResponse.fail(errorCode, error))
    }

    @ExceptionHandler(BaseException::class)
    private fun handleBizException(e: BaseException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorCode = e.errorCode
        printErrorLog(errorCode, request, e)

        return ResponseEntity.status(errorCode.status)
            .body(ErrorResponse.fail(errorCode))
    }

    @ExceptionHandler(Exception::class)
    private fun handleException(e: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR
        printErrorLog(errorCode, request, e)

        return ResponseEntity.status(errorCode.status)
            .body(ErrorResponse.fail(errorCode))
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