package com.loopin.api.loopinbackend.common.error.handler

import com.loopin.api.loopinbackend.common.error.exception.BaseException
import com.loopin.api.loopinbackend.common.response.ErrorResponse
import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)

    // 잘못된 경로로 요청
    @ExceptionHandler(NoHandlerFoundException::class, NoResourceFoundException::class)
    fun handleNotFound(e: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.NOT_FOUND
        printErrorLog(errorCode, request, e)

        return ResponseEntity
            .status(errorCode.status)
            .body(ErrorResponse.fail(code = errorCode))
    }

    // 요청 body 오류
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleNotReadable(e: HttpMessageNotReadableException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.NO_READABLE_BODY
        printErrorLog(errorCode, request, e)

        return ResponseEntity
            .status(errorCode.status)
            .body(ErrorResponse.fail(code = errorCode))
    }

    // 잘못된 메서드로 요청 예) GET 으로 요청해야하는데 POST 로 요청
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotAllowed(
        e: HttpRequestMethodNotSupportedException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.METHOD_NOT_ALLOWED

        printErrorLog(errorCode, request, e)

        return ResponseEntity
            .status(errorCode.status)
            .body(ErrorResponse.fail(code = errorCode))
    }

    // 사용자 요청 예외 (Validation)
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(e: ConstraintViolationException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.INVALID_INPUT_VALUE
        printErrorLog(errorCode, request, e)

        val body = ErrorResponse.fail(code = errorCode).apply {
            message = e.message ?: message
        }

        return ResponseEntity.status(errorCode.status).body(body)
    }

    // 사용자 요청 예외 (Validation)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleRequestBodyValidation(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val errorCode = ErrorCode.INVALID_INPUT_VALUE
        printErrorLog(errorCode, request, e)

        val fields: Map<String, List<String>> =
            e.bindingResult.fieldErrors
                .groupBy { it.field }
                .mapValues { (_, errors) ->
                    errors.mapNotNull { it.defaultMessage }
                }

        return ResponseEntity
            .status(errorCode.status) // 보통 400
            .body(
                ErrorResponse.fail(
                    code = errorCode,
                    errors = fields
                )
            )
    }


    // 사용자 노출 예외
    @ExceptionHandler(BaseException::class)
    private fun handleBizException(e: BaseException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorCode = e.errorCode
        printErrorLog(errorCode, request, e)

        return ResponseEntity
            .status(errorCode.status)
            .body(
                ErrorResponse.fail(
                    code = errorCode,
                    errors = e.fields
                )
            )
    }

    // 시스템 예외
    @ExceptionHandler(Exception::class)
    private fun handleException(e: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR
        printErrorLog(errorCode, request, e)

        return ResponseEntity.status(errorCode.status).body(ErrorResponse.fail(errorCode))
    }

    private fun printErrorLog(errorCode: ErrorCode, request: HttpServletRequest, e: Exception?) {
        log.warn(
            "[{}] {}: {} - {} {}",
            errorCode.code,
            errorCode.status,
            errorCode.message,
            request.method,
            request.requestURI,
            e
        )
//        when (e) {
//            is BaseException -> {
//                log.warn("[{}] {}: {} - {} {}", errorCode.code, errorCode.status,
//                errorCode.message, request.method, request.requestURI)
//            } else -> {
//                log.error("[{}] {}: {} - {} {}", errorCode.code, errorCode.status,
//                errorCode.message, request.method, request.requestURI, e)
//            }
//        }
    }
}