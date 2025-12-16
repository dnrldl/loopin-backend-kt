package com.loopin.api.loopinbackend

import com.loopin.api.loopinbackend.global.security.jwt.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(JwtProperties::class)
@SpringBootApplication
class LoopinBackendApplication

fun main(args: Array<String>) {
    runApplication<LoopinBackendApplication>(*args)
}
