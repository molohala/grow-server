package com.molohala.grow.core.comment.application.dto.req

data class CommentModifyReq(
    val commentId: Long,
    val content: String
)
