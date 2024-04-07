package com.molohala.infinityinfra.webclient.exception

import com.molohala.infinitycommon.exception.GlobalExceptionCode
import com.molohala.infinitycommon.exception.custom.CustomException

class WebClientException(code: Int) : CustomException(
    when (code) {
        400 -> GlobalExceptionCode.TOKEN_NOT_PROVIDED
        401, 500 -> GlobalExceptionCode.INVALID_TOKEN
        410 -> GlobalExceptionCode.TOKEN_EXPIRED
        else -> GlobalExceptionCode.INTERNAL_SERVER
    }
)