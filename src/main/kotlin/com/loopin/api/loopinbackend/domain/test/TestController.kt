package com.loopin.api.loopinbackend.domain.test

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test")
class TestController {

    @GetMapping
    fun test(): ResponseEntity<String> = ResponseEntity.ok("Hello World!")
}