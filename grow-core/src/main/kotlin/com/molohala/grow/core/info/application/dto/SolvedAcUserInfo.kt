package com.molohala.grow.core.info.application.dto

data class SolvedAcUserInfo(
    val name: String,
    val avatarUrl: String?,
    val bio: String,
    val tier: Int,
    val rating: Int,
    val maxStreak: Int,
    val totalRank: Long,
    val totalSolves: Long
)
