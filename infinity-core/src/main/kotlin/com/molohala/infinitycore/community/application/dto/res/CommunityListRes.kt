package com.molohala.infinitycore.community.application.dto.res

import com.molohala.infinitycore.comment.application.dto.res.CommentRes

data class CommunityListRes(
    val community: CommunityRes,
    val recentComment: CommentRes?
) {
}