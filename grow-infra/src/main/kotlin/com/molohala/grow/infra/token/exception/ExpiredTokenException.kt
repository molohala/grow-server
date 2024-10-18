package com.molohala.grow.infra.token.exception

import com.molohala.grow.common.exception.GlobalExceptionCode
import com.molohala.grow.common.exception.custom.CustomException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ExpiredTokenException : CustomException(GlobalExceptionCode.TOKEN_EXPIRED)
