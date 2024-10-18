package com.molohala.grow.core.report.domain.entity

import com.molohala.grow.core.common.BaseIdAndTimeEntity
import com.molohala.grow.core.report.domain.consts.ReportState
import com.molohala.grow.core.report.domain.consts.ReportType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Suppress("unused")
@Entity
data class Report(
    @Column(nullable = false)
    val reportedId: Long,

    @Column(nullable = false)
    val reason: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val reportType: ReportType,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val state: ReportState
) : BaseIdAndTimeEntity(null, null)
