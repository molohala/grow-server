package com.molohala.infinityinfra.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.molohala.infinitycommon.exception.ErrorResponseEntity
import com.molohala.infinitycommon.exception.ExceptionCode
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class ErrorResponseSender(private val objectMapper: ObjectMapper) {

    fun send(response: HttpServletResponse, code: ExceptionCode) {
        val entity = getErrorResponseEntity(code)
        try {
            response.status = entity.status
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.characterEncoding = "UTF-8"
            response.writer.write(objectMapper.writeValueAsString(entity))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getErrorResponseEntity(code: ExceptionCode): ErrorResponseEntity {
        return ErrorResponseEntity(
            code.getHttpStatus().value(),
            code.getExceptionName(),
            code.getMessage(),
        )
    }
}
