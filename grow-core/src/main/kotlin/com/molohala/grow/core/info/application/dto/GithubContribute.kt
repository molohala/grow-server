package com.molohala.grow.core.info.application.dto

import java.time.LocalDate

data class GithubContribute(
    val date: LocalDate,
    val contributionCount: Long
)