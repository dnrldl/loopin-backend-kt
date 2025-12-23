package com.loopin.api.loopinbackend.common.web.client

import com.loopin.api.loopinbackend.common.error.exception.BusinessException
import com.loopin.api.loopinbackend.common.logging.logger
import com.loopin.api.loopinbackend.common.response.code.ErrorCode
import io.netty.handler.timeout.ReadTimeoutException
import io.netty.handler.timeout.TimeoutException
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientRequestException
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class ApiClient(
    private val webClient: WebClient,
) {
    private val log = logger()

    fun <REQ : Any, RES : Any> post(
        url: String,
        body: REQ,
        responseType: Class<RES>,
        headers: Map<String, String> = emptyMap()
    ): Mono<RES> {
        return webClient.post()
            .uri(url)
            .headers { h -> headers.forEach(h::add) }
            .bodyValue(body)
            .retrieve()
            .onStatus(
                { it.is4xxClientError || it.is5xxServerError }
            ) { response ->
                response.bodyToMono<String>()
                    .defaultIfEmpty("")
                    .flatMap { errorBody ->
                        log.error(
                            """
                        [EXTERNAL API ERROR]
                        url=$url
                        status=${response.statusCode()}
                        body=$errorBody
                        """.trimIndent()
                        )
                        Mono.error(BusinessException(ErrorCode.EXTERNAL_API_ERROR))
                    }
            }
            .bodyToMono(responseType)
            .timeout(Duration.ofSeconds(3))
            .onErrorMap { ex ->
                when (ex) {
                    is ReadTimeoutException,
                    is TimeoutException ->
                        BusinessException(ErrorCode.EXTERNAL_API_TIMEOUT)
                    is WebClientRequestException ->
                        BusinessException(ErrorCode.EXTERNAL_API_ERROR)
                    else -> ex
                }
            }
    }

}
