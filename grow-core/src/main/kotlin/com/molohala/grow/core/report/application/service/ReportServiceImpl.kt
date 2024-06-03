package com.molohala.grow.core.report.application.service

import com.molohala.grow.common.exception.GlobalExceptionCode
import com.molohala.grow.common.exception.custom.CustomException
import com.molohala.grow.core.comment.repository.CommentJpaRepository
import com.molohala.grow.core.community.repository.CommunityJpaRepository
import com.molohala.grow.core.report.domain.consts.ReportState
import com.molohala.grow.core.report.domain.consts.ReportType
import com.molohala.grow.core.report.domain.entity.Report
import com.molohala.grow.core.report.exception.ReportExceptionCode
import com.molohala.grow.core.report.repository.ReportJpaRepository
import com.molohala.grow.core.report.repository.ReportQueryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReportServiceImpl(
    private val reportJpaRepository: ReportJpaRepository,
    private val reportQueryRepository: ReportQueryRepository,
    private val communityJpaRepository: CommunityJpaRepository,
    private val commentJpaRepository: CommentJpaRepository,
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

    @Transactional(rollbackFor = [Exception::class])
    override fun mange(id: Long, reportState: ReportState) {
        if (reportState == ReportState.PENDING)
            throw CustomException(ReportExceptionCode.REPORT_CANNOT_BE_UNDONE)

        val report = reportQueryRepository.findReportByReportedId(id)
            ?: throw CustomException(ReportExceptionCode.REPORT_NOT_FOUND)

        if (report.state != ReportState.PENDING)
            throw CustomException(ReportExceptionCode.REPORT_ALREADY_MANAGED)

        reportJpaRepository.save(report.copy(state = reportState))
        when (report.reportType) {
            ReportType.COMMUNITY -> {
                val community = communityJpaRepository.findByIdOrNull(report.reportedId)
                    ?: throw CustomException(GlobalExceptionCode.INTERNAL_SERVER)
                community.markReported()
                communityJpaRepository.save(community)
            }
            ReportType.COMMENT -> {
                val comment = commentJpaRepository.findByIdOrNull(report.reportedId)
                    ?: throw CustomException(GlobalExceptionCode.INTERNAL_SERVER)
                comment.markReported()
                commentJpaRepository.save(comment)
            }
        }
    }
}
