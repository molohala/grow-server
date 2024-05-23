package com.molohala.grow.core.report.application.service

import com.molohala.grow.core.report.domain.consts.ReportState
import com.molohala.grow.core.report.domain.consts.ReportType
import com.molohala.grow.core.report.domain.entity.Report
import com.molohala.grow.core.report.repository.ReportJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReportServiceImpl(
    private val reportJpaRepository: ReportJpaRepository,
) : ReportService {
    override fun listReports(): List<Report> {
        return reportJpaRepository.findAll()
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun report(id: Long, reason: String, type: ReportType) {
        reportJpaRepository.save(
            Report(reportedId = id, reason = reason, reportType = type, state = ReportState.PENDING)
        )
    }
}
