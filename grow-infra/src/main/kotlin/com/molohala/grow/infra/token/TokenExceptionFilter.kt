package com.molohala.grow.infra.token

import com.molohala.grow.common.exception.GlobalExceptionCode
import com.molohala.grow.common.exception.custom.CustomException
import com.molohala.grow.infra.security.ErrorResponseSender
import com.molohala.grow.infra.webclient.exception.WebClientException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class TokenExceptionFilter(private val errorResponseSender: ErrorResponseSender) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: CustomException) {
            errorResponseSender.send(response, e.exceptionCode)
        } catch (e: WebClientException) {
            errorResponseSender.send(response, e.exceptionCode)
        } catch (e: Exception) {
            logger.error("error: ", e)
            errorResponseSender.send(response, GlobalExceptionCode.INTERNAL_SERVER)
        }
    }
}