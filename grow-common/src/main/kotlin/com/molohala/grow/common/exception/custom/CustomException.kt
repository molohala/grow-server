package com.molohala.grow.common.exception.custom

import com.molohala.grow.common.exception.ExceptionCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
open class CustomException(
    val exceptionCode: ExceptionCode
): RuntimeException()