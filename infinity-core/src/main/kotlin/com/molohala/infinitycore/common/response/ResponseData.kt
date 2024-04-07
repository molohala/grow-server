package com.molohala.infinitycore.common.response

import org.springframework.http.HttpStatus

data class ResponseData<T>(val status: Int, val message: String, val data: T?) {
    constructor(status: HttpStatus, message: String, data: T?) : this(status.value(), message, data)

    companion object {
        fun <T> toResponse(status: HttpStatus, message: String, data: T? = null): ResponseData<T> {
            return ResponseData(status, message, data)
        }
    }
}
