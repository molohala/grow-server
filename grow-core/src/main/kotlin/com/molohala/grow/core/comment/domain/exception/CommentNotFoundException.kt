package com.molohala.grow.core.comment.domain.exception

import com.molohala.grow.common.exception.GlobalExceptionCode
import com.molohala.grow.common.exception.custom.CustomException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class CommentNotFoundException : CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND)