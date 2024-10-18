package com.molohala.grow.core.rank.domain.dto.res

import java.time.LocalDateTime

data class RankingTimedListRes(
    val updatedAt: LocalDateTime,
    val ranks: List<RankingRes>
)
