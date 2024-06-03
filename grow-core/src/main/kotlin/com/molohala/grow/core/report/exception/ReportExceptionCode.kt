package com.molohala.grow.core.report.exception

import com.molohala.grow.common.exception.ExceptionCode
import org.springframework.http.HttpStatus

enum class ReportExceptionCode(private val status: HttpStatus, private val message: String) : ExceptionCode {
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 신고를 찾을 수 없음"),
    REPORT_ALREADY_MANAGED(HttpStatus.BAD_REQUEST, "신고가 이미 처리 되었음"),
    REPORT_CANNOT_BE_UNDONE(HttpStatus.BAD_REQUEST, "신고는 철회 할 수 없음"),
    ;

    override fun getHttpStatus() = status
    override fun getExceptionName() = name
    override fun getMessage() = message
}
