package com.molohala.infinitycore.community.application.dto.res

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class CommunityListRes @QueryProjection constructor(
    val communityId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val like: Long,
    val writerName: String,
    val writerId: Long
)