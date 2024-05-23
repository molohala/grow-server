package com.molohala.grow.core.report.domain.entity

import com.molohala.grow.core.common.BaseIdAndTimeEntity
import com.molohala.grow.core.report.domain.consts.ReportState
import com.molohala.grow.core.report.domain.consts.ReportType
import jakarta.persistence.Entity

@Entity
class Report(
    val reportedId: Long,
    val reason: String,
    val reportType: ReportType,
    state: ReportState
) : BaseIdAndTimeEntity(null, null) {
    var state = state
    private set

    fun updateState(state: ReportState) {
        this.state = state
    }
}
