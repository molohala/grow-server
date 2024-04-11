package com.molohala.infinitycore.like.repository

import com.molohala.infinitycore.like.domain.entity.Like

interface QueryLikeRepository {
    fun existsByCommunityIdAndMemberId(communityId:Long, memberId:Long): Boolean
    fun findByCommunityIdAndMemberId(communityId:Long, memberId: Long): Like
    fun getCntByCommunityId(communityId:Long): Long

}