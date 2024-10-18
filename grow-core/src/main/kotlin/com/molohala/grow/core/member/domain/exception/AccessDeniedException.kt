package com.molohala.grow.core.member.domain.exception

import com.molohala.grow.common.exception.GlobalExceptionCode
import com.molohala.grow.common.exception.custom.CustomException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.FORBIDDEN)
class AccessDeniedException : CustomException(GlobalExceptionCode.INVALID_ROLE)