package com.molohala.grow.infra.webclient.exception

import com.molohala.grow.common.exception.GlobalExceptionCode
import com.molohala.grow.common.exception.custom.CustomException

class WebClientException(code: Int) : CustomException(
    when (code) {
        400 -> GlobalExceptionCode.TOKEN_NOT_PROVIDED
        403 -> GlobalExceptionCode.INVALID_TOKEN
        401, 500 -> GlobalExceptionCode.INVALID_TOKEN
        410 -> GlobalExceptionCode.TOKEN_EXPIRED
        else -> GlobalExceptionCode.INTERNAL_SERVER
    }
)
