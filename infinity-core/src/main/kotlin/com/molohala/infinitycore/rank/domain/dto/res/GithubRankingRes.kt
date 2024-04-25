package com.molohala.infinitycore.rank.domain.dto.res

data class GithubRankingRes(
    val memberId: Long,
    val socialId: String,
    val rank: Int,
    val commits: Long,
)