package com.molohala.infinitycore.like.repository

import com.molohala.infinitycore.like.domain.dto.LikeCount

interface LikeCachedRepository {
    fun cache(communityId: Long, likedCount: Long)
    fun clear(communityId: Long)
    fun getAll(count: Long): List<LikeCount>
}