package com.molohala.infinitycore.info.exception

import com.molohala.infinitycommon.exception.ExceptionCode
import org.springframework.http.HttpStatus

enum class InfoExceptionCode(
    private val status: HttpStatus,
    private val message: String
) : ExceptionCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없음"),
    NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED, "구현되지 않음");

    override fun getHttpStatus(): HttpStatus {
        return status
    }

    override fun getExceptionName(): String {
        return name
    }

    override fun getMessage(): String {
        return message
    }

}