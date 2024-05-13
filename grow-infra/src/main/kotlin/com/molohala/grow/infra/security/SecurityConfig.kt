package com.molohala.grow.infra.security

import com.molohala.grow.common.exception.GlobalExceptionCode
import com.molohala.grow.infra.token.TokenExceptionFilter
import com.molohala.grow.infra.token.TokenFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val errorResponseSender: ErrorResponseSender,
    private val tokenFilter: TokenFilter,
    private val tokenExceptionFilter: TokenExceptionFilter
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .httpBasic { it.disable() }
            .cors { }
            .csrf { it.disable() }
            .addFilterAfter(tokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(tokenExceptionFilter, TokenFilter::class.java)
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/auth/sign-in", "/auth/reissue").permitAll()
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                    .anyRequest().authenticated()
            }
            .exceptionHandling {
                it
                    .accessDeniedHandler { _, response, _ ->
                        errorResponseSender.send(response, GlobalExceptionCode.INVALID_ROLE)
                    }
                    .authenticationEntryPoint { req, res, _ ->
                        errorResponseSender.send(
                            res,
                            if (req.getHeader("Authorization") != null) GlobalExceptionCode.INVALID_TOKEN
                            else GlobalExceptionCode.TOKEN_NOT_PROVIDED
                        )
                    }
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