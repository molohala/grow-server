package com.molohala.grow.core.report.repository

import com.molohala.grow.core.report.domain.entity.QReport.report
import com.molohala.grow.core.report.domain.entity.Report
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ReportQueryDslRepository(private val queryFactory: JPAQueryFactory) : ReportQueryRepository {
    override fun findReportByReportedId(reportId: Long): Report? {
        return queryFactory
            .select(report)
            .from(report)
            .where(report.reportedId.eq(reportId))
            .fetchFirst()
    }
}
