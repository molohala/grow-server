package com.molohala.infinitycore.common.response

import org.springframework.http.HttpStatus

data class Response(val status: Int, val message: String) {
    constructor(status: HttpStatus, message: String) : this(status.value(), message)

    companion object {
        fun toResponse(status: HttpStatus, message: String): Response {
            return Response(status, message)
        }
    }
}