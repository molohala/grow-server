package com.molohala.grow.core.comment.repository

import com.molohala.grow.core.comment.application.dto.res.CommentRes

interface QueryCommentRepository {
    fun findByCommunityId(communityId: Long): List<CommentRes>?
    fun findRecentComment(communityId: Long): CommentRes?
}