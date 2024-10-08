package com.molohala.infinityinfra.security

import com.molohala.infinitycommon.exception.GlobalExceptionCode
import com.molohala.infinityinfra.token.TokenExceptionFilter
import com.molohala.infinityinfra.token.TokenFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
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
            .cors {  }
            .csrf { it.disable() }
            .addFilterAfter(tokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(tokenExceptionFilter, TokenFilter::class.java)
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("auth/**").permitAll()
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                    .anyRequest().authenticated()
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