package com.molohala.grow.core.comment.application.dto.res

import java.time.LocalDateTime

data class CommentRes(
    val commentId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val memberId: Long,
    val name: String
)