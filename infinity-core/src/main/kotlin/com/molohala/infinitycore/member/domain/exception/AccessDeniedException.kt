package com.molohala.infinitycore.member.domain.exception

import com.molohala.infinitycommon.exception.GlobalExceptionCode
import com.molohala.infinitycommon.exception.custom.CustomException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.FORBIDDEN)
class AccessDeniedException: CustomException(GlobalExceptionCode.INVALID_ROLE)