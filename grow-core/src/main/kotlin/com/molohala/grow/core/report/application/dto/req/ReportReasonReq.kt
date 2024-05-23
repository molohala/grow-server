package com.molohala.grow.core.report.application.dto.req

import com.fasterxml.jackson.annotation.JsonCreator

data class ReportReasonReq @JsonCreator constructor(
    val reason: String
)
