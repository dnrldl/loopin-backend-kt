package com.loopin.api.loopinbackend.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): DefaultSecurityFilterChain? {
        http
            .cors { it.disable() }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .logout { it.disable() }
            .httpBasic { it.disable() }

            .authorizeHttpRequests {
                it.requestMatchers("/api/auth/**").permitAll()

                // 중복 체크 허용
                it.requestMatchers("/api/users/check/**").permitAll()

                // Swagger 문서 허용
                it.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // 회원가입 허용
                it.requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()

                // 유저 조회 허용
                it.requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()

                // 게시글 조회 허용
                it.requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                it.requestMatchers(HttpMethod.POST, "/api/posts").authenticated()

                // 댓글 조회 허용
                it.requestMatchers(HttpMethod.GET, "/api/posts/*/comments").permitAll()

                it.anyRequest().authenticated()
            }

            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

//            .addFilterBefore(
//                jwtAuthenticationFilter,
//                UsernamePasswordAuthenticationFilter::class.java
//            )

//            .exceptionHandling {
//                it.authenticationEntryPoint(authenticationEntryPoint) // 401
//                it.accessDeniedHandler(accessDeniedHandler)             // 403
//            }

        // OAuth2 설정은 여기서 이어서

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager = config.authenticationManager
}