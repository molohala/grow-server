package com.molohala.grow.core.community.application.dto.res

import com.molohala.grow.core.comment.application.dto.res.CommentRes

data class CommunityListRes(
    val community: CommunityRes,
    val recentComment: CommentRes?
)