package com.molohala.infinitycore.comment.repository

import com.molohala.infinitycore.comment.application.dto.res.CommentRes

interface QueryCommentRepository {
    fun findByCommunityId(communityId: Long):List<CommentRes>?
    fun findRecentComment(communityId: Long):CommentRes
}