package com.molohala.grow.core.language.exception

import com.molohala.grow.common.exception.ExceptionCode
import org.springframework.http.HttpStatus

enum class LanguageExceptionCode(private val status: HttpStatus, private val message: String) : ExceptionCode {
    LANGUAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당하는 언어를 찾을 수 없음"),
    ;

    override fun getHttpStatus() = status
    override fun getExceptionName() = name
    override fun getMessage() = message
}