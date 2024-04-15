package com.molohala.infinityinfra.token

import com.molohala.infinitycommon.exception.GlobalExceptionCode
import com.molohala.infinitycommon.exception.custom.CustomException
import com.molohala.infinityinfra.security.ErrorResponseSender
import com.molohala.infinityinfra.webclient.exception.WebClientException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class TokenExceptionFilter(private val errorResponseSender: ErrorResponseSender) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: CustomException) {
          errorResponseSender.send(response, e.exceptionCode)
        } catch (e: WebClientException) {
            errorResponseSender.send(response, e.exceptionCode)
        } catch (e: Exception) {
            errorResponseSender.send(response, GlobalExceptionCode.INTERNAL_SERVER)
        }
    }
}