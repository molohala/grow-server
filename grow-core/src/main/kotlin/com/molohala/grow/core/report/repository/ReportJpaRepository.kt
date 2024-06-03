package com.molohala.grow.core.report.repository

import com.molohala.grow.core.report.domain.entity.Report
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReportJpaRepository : JpaRepository<Report, Long>
