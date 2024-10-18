package com.molohala.grow.common.exception.custom

import com.molohala.grow.common.exception.GlobalExceptionCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
class InternalServerException : CustomException(GlobalExceptionCode.INTERNAL_SERVER)
