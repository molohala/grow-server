package com.molohala.grow.core.community.repository

import com.molohala.grow.core.common.PageRequest
import com.molohala.grow.core.community.application.dto.res.CommunityRes

interface CommunityQueryRepository {
    fun findWithPagination(pageRequest: PageRequest, userId: Long): List<CommunityRes>
    fun findById(id: Long, likeCnt: Long, isLike: Boolean): CommunityRes?
}