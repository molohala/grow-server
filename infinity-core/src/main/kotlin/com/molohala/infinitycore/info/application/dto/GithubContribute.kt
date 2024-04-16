package com.molohala.infinitycore.info.application.dto

import java.time.LocalDate

data class GithubContribute(
    val date: LocalDate,
    val contributionCount: Long
)