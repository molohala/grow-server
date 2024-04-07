package com.molohala.infinitycommon.exception

import org.springframework.http.HttpStatus

interface ExceptionCode {
    fun getHttpStatus(): HttpStatus
    fun getExceptionName(): String
    fun getMessage(): String
}