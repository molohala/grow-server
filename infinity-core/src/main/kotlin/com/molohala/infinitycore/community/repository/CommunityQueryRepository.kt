package com.molohala.infinitycore.community.repository

import com.molohala.infinitycore.common.PageRequest
import com.molohala.infinitycore.community.application.dto.res.CommunityRes

interface CommunityQueryRepository {
    fun findWithPagination(pageRequest: PageRequest):List<CommunityRes>
    fun findById(id: Long, likeCnt:Long, isLike: Boolean): CommunityRes?
}