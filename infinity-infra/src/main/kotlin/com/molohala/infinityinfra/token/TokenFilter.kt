package com.molohala.infinityinfra.token

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class TokenFilter(
    private val tokenExtractor: TokenExtractor
) : OncePerRequestFilter() {

    companion object {
        private const val TOKEN_TYPE = "Bearer"
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = tokenExtractor.extract(request, TOKEN_TYPE)
        if (token.isNotEmpty()) {
            val authentication: Authentication = tokenExtractor.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }
}