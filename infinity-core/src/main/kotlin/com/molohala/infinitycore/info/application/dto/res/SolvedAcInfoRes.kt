package com.molohala.infinitycore.info.application.dto.res

import com.molohala.infinitycore.info.application.dto.SolvedAcSolves

data class SolvedAcInfoRes(
    val name: String,
    val avatarUrl: String?,
    val bio: String,
    val tier: Int,
    val rating: Int,
    val maxStreak: Int,
    val totalRank: Long,

    val totalSolves: Long,
    val weekSolves: List<SolvedAcSolves>,
    val todaySolves: SolvedAcSolves
)
