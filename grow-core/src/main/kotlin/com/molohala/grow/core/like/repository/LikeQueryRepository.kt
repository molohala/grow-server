package com.molohala.grow.core.like.repository

import com.molohala.grow.core.like.domain.entity.Like

interface LikeQueryRepository {
    fun existsByCommunityIdAndMemberId(communityId: Long, memberId: Long): Boolean
    fun findByCommunityIdAndMemberId(communityId: Long, memberId: Long): Like
    fun getCntByCommunityId(communityId: Long): Long

}