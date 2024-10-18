package com.molohala.grow.core.like.repository

import com.molohala.grow.core.like.domain.dto.LikeCount

interface LikeCachedRepository {
    fun cache(communityId: Long, likedCount: Long)
    fun clear(communityId: Long)
    fun getAll(count: Long): List<LikeCount>
}