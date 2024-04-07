package com.molohala.infinityinfra.security

import com.molohala.infinitycommon.exception.GlobalExceptionCode
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val errorResponseSender: ErrorResponseSender
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .httpBasic { it.disable() }
            .cors {  }
            .csrf { it.disable() }
            .authorizeHttpRequests { authorize ->
                authorize
//                    .requestMatchers("")
//                    .permitAll()
                    .anyRequest().permitAll()
            }
            .exceptionHandling {
                it.accessDeniedHandler { _, response, _ ->
                    errorResponseSender.send(response, GlobalExceptionCode.INVALID_ROLE)
                }
                    .authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.NOT_FOUND))
            }
            .build()


    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.addAllowedOriginPattern("*")
        configuration.addAllowedHeader("*")
        configuration.addAllowedMethod("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}