package com.molohala.infinitycore.community.application.dto.res

import java.time.LocalDateTime

data class CommunityListRes(
    val communityId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val like: Long,
    val writer: String
)