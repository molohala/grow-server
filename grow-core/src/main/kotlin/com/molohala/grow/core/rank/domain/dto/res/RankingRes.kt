package com.molohala.grow.core.rank.domain.dto.res

data class RankingRes(
    val memberId: Long,
    val memberName: String,
    val socialId: String,
    val rank: Int,
    val isBlockedUser: Boolean,
    val count: Long,
)
