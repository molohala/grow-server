package com.molohala.grow.core.report.repository

import com.molohala.grow.core.report.domain.entity.Report

interface ReportQueryRepository {
    fun findReportByReportedId(reportId: Long): Report?
}
