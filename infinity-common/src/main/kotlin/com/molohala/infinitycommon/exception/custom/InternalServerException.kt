package com.molohala.infinitycommon.exception.custom

import com.molohala.infinitycommon.exception.GlobalExceptionCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
class InternalServerException : CustomException(GlobalExceptionCode.INTERNAL_SERVER)
