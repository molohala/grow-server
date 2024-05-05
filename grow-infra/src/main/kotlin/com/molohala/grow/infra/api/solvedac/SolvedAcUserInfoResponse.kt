package com.molohala.grow.infra.api.solvedac

import java.time.LocalDateTime

data class SolvedAcUserInfoResponse(
    val handle: String,
    val bio: String,
    val badgeId: String?,
    val backgroundId: String,
    val profileImageUrl: String?,
    val solvedCount: Long,
    val voteCount: Int,
    val `class`: Int,
    val classDecoration: String,
    val rivalCount: Int,
    val reverseRivalCount: Int,
    val tier: Int,
    val rating: Int,
    val ratingByProblemsSum: Int,
    val ratingByClass: Int,
    val ratingBySolvedCount: Int,
    val ratingByVoteCount: Int,
    val arenaTier: Int,
    val arenaRating: Int,
    val arenaMaxTier: Int,
    val arenaMaxRating: Int,
    val arenaCompetedRoundCount: Int,
    val maxStreak: Int,
    val coins: Long,
    val stardusts: Long,
    val joinedAt: LocalDateTime,
    val bannedUntil: LocalDateTime,
    val proUntil: LocalDateTime,
    val rank: Long,
    val isRival: Boolean,
    val isReverseRival: Boolean,
    val blocked: Boolean,
    val reverseBlocked: Boolean
)