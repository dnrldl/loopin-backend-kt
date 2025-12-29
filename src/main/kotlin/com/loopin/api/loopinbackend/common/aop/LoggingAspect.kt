package com.loopin.api.loopinbackend.common.aop

import com.fasterxml.jackson.databind.ObjectMapper
import com.loopin.api.loopinbackend.common.logging.logger
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import java.util.stream.IntStream

@Aspect
@Component
class LoggingAspect(
    private val objectMapper: ObjectMapper
) {
    val log = logger()

    @Pointcut("execution(* com.loopin.api.loopinbackend.domain..controller..*(..)))")
    fun controllerMethod() {
    }

    @Pointcut("execution(* com.loopin.api.loopinbackend.domain..service..*(..)))")
    fun serviceMethod() {
    }

    @Before("controllerMethod()")
    fun logControllerRequest(joinPoint: JoinPoint) {
        val methodName = getMethodName(joinPoint)
        val args = getArguments(joinPoint)
        log.debug("[REQUEST] {}({})", methodName, args)
    }

    @AfterReturning(pointcut = "controllerMethod()", returning = "result")
    fun logControllerResponse(joinPoint: JoinPoint, result: Any) {
        try {
            val methodName = getMethodName(joinPoint)
            val responseResult = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result)

            log.debug("[RESPONSE] {} =>\n\r{}", methodName, responseResult)
        } catch (e: Exception) {
            log.warn("[RESPONSE LOGGING ERROR] {}", e.message)
        }
    }

    private fun getMethodName(joinPoint: JoinPoint): String =
        "${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name}"

    private fun getArguments(joinPoint: JoinPoint): String {
        val signature = joinPoint.signature as MethodSignature
        val paramNames = signature.parameterNames
        val args = joinPoint.args

        if (paramNames.isEmpty() || paramNames.size == 0) return ""

        val sb = StringBuilder()
        IntStream.range(0, paramNames.size).forEach { i: Int ->
            try {
                sb.append(paramNames[i]).append("=").append(args[i])
                if (i < paramNames.size - 1) sb.append(", ")
            } catch (e: Exception) {
                sb.append(paramNames[i]).append("=<?>")
            }
        }
        return sb.toString()
    }
}