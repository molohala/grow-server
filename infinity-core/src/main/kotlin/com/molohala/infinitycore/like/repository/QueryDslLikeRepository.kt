package com.molohala.infinitycore.like.repository

import com.molohala.infinitycore.like.domain.entity.Like
import com.molohala.infinitycore.like.domain.entity.QLike.like
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class QueryDslLikeRepository(
    private val queryFactory: JPAQueryFactory
): QueryLikeRepository {
    override fun existsByCommunityIdAndMemberId(communityId: Long, memberId: Long): Boolean {
        return queryFactory
            .selectOne()
            .from(like)
            .where(like.memberId.eq(memberId).and(like.communityId.eq(communityId)))
            .fetchOne() != null
    }

    override fun findByCommunityIdAndMemberId(communityId: Long, memberId: Long): Like {
        return queryFactory
            .select(like)
            .from(like)
            .where(like.memberId.eq(memberId).and(like.communityId.eq(communityId)))
            .fetchFirst()
    }

    override fun getCntByCommunityId(communityId: Long): Long {
        return queryFactory
            .selectFrom(like)
            .where(like.communityId.eq(communityId))
            .fetchCount()
    }
}