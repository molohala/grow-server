package com.molohala.infinitycore.community.domain.exception

import com.molohala.infinitycommon.exception.GlobalExceptionCode
import com.molohala.infinitycommon.exception.custom.CustomException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class CommunityNotFoundException: CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND)