package com.molohala.infinityinfra.token.exception

import com.molohala.infinitycommon.exception.GlobalExceptionCode
import com.molohala.infinitycommon.exception.custom.CustomException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ExpiredTokenException : CustomException(GlobalExceptionCode.TOKEN_EXPIRED) {
}
