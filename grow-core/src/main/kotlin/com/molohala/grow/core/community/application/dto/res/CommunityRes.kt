package com.molohala.grow.core.community.application.dto.res

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class CommunityRes @QueryProjection constructor(
    val communityId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val like: Long,
    val isLiked: Boolean,
    val writerName: String,
    val writerId: Long
)