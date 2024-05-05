package com.molohala.grow.common.exception

import org.springframework.http.ResponseEntity

data class ErrorResponseEntity(
    val status: Int,
    val code: String,
    val message: String
) {
    companion object {
        fun responseEntity(e: ExceptionCode): ResponseEntity<ErrorResponseEntity> {
            return ResponseEntity
                .status(e.getHttpStatus())
                .body(
                    ErrorResponseEntity(
                        status = e.getHttpStatus().value(),
                        code = e.getExceptionName(),
                        message = e.getMessage()
                    )
                )
        }
    }
}