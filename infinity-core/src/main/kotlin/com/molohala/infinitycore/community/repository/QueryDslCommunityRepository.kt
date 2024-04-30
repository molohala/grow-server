package com.molohala.infinitycore.community.repository

import com.molohala.infinitycore.common.PageRequest
import com.molohala.infinitycore.community.application.dto.res.CommunityRes
import com.molohala.infinitycore.community.domain.consts.CommunityState
import com.molohala.infinitycore.community.domain.entity.Community
import com.molohala.infinitycore.community.domain.entity.QCommunity.community
import com.molohala.infinitycore.member.domain.entity.QMember.member
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class QueryDslCommunityRepository(
    private val queryFactory: JPAQueryFactory
): QueryCommunityRepository {
    val query: JPAQuery<Community> = queryFactory.select(community)
        .from(community)

    override fun findWithPagination(pageRequest: PageRequest): List<CommunityRes> {
        return queryFactory
            .select(
                communityProjection(0, false)
            )
            .from(community)
            .where(community.state.ne(CommunityState.DELETED))
            .orderBy(community.createdAt.desc())
            .innerJoin(member).on(community.memberId.eq(member.id))
            .offset((pageRequest.page - 1) * pageRequest.size)
            .limit(pageRequest.size)
            .fetch()
    }

    override fun findById(id: Long, likeCnt:Long, isLike:Boolean): CommunityRes? {
        return queryFactory
            .select(communityProjection(likeCnt, isLike))
            .from(community)
            .innerJoin(member).on(community.memberId.eq(member.id))
            .where(community.id.eq(id).and(community.state.ne(CommunityState.DELETED)))
            .fetchFirst()
    }

    private fun communityProjection(likeCount: Long, isLike: Boolean): ConstructorExpression<CommunityRes> {
        return Projections.constructor(
            CommunityRes::class.java,
            community.id,
            community.content,
            community.createdAt,
            Expressions.constant(likeCount),
            Expressions.constant(isLike),
            member.name,
            member.id
        )
    }
}