package com.molohala.grow.common.exception

import org.springframework.http.HttpStatus

@Suppress("unused")
enum class GlobalExceptionCode(
    private val status: HttpStatus,
    private val message: String
) : ExceptionCode {

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 데이터"),
    METHOD_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "잘못된 메서드"),
    MEDIA_TYPE_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "잘못된 미디어 타입"),
    MEDIA_TYPE_MISS_MATCHED(HttpStatus.BAD_REQUEST, "잘못된 미디어 값"),
    PARAMETER_NOT_FOUND(HttpStatus.BAD_REQUEST, "잘못된 파라미터"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 파라미터"),
    INVALID_PERMISSION(HttpStatus.BAD_REQUEST, "유효하지 않은 권한"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰"),
    INVALID_ROLE(HttpStatus.FORBIDDEN, "유효하지 않은 권한"),
    TOKEN_NOT_PROVIDED(HttpStatus.BAD_REQUEST, "잘못된 토큰"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰"),
    INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류");

    override fun getHttpStatus(): HttpStatus {
        return status
    }

    override fun getExceptionName(): String {
        return name
    }

    override fun getMessage(): String {
        return message
    }
}
