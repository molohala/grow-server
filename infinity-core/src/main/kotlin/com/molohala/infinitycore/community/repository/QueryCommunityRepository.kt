package com.molohala.infinitycore.community.repository

import com.molohala.infinitycore.common.PageRequest
import com.molohala.infinitycore.community.application.dto.res.CommunityListRes

interface QueryCommunityRepository {
    fun findWithPagination(pageRequest: PageRequest):List<CommunityListRes>
    fun findById(id: Long): CommunityListRes?
}