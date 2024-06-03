package com.molohala.grow.core.report.application.service

import com.molohala.grow.core.report.domain.consts.ReportState
import com.molohala.grow.core.report.domain.consts.ReportType
import com.molohala.grow.core.report.domain.entity.Report

interface ReportService {
    fun listReports(): List<Report>
    fun report(id: Long, reason: String, type: ReportType)
    fun mange(id: Long, reportState: ReportState)
}
