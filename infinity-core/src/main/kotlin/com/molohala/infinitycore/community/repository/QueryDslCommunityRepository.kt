package com.molohala.infinitycore.community.repository

import com.molohala.infinitycore.common.PageRequest
import com.molohala.infinitycore.community.application.dto.res.CommunityListRes
import com.molohala.infinitycore.community.domain.entity.Community
import com.molohala.infinitycore.community.domain.entity.QCommunity.community
import com.molohala.infinitycore.member.domain.entity.QMember.member
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class QueryDslCommunityRepository(
    private val queryFactory: JPAQueryFactory
): QueryCommunityRepository {
    val query: JPAQuery<Community> = queryFactory.select(community)
        .from(community)

    override fun findWithPagination(pageRequest: PageRequest): MutableList<CommunityListRes> {
        return queryFactory
            .select(communityRes())
            .from(community)
            .innerJoin(member)
            .on(community.memberId.eq(member.id))
            .offset((pageRequest.page - 1) * pageRequest.size)
            .limit(pageRequest.size)
            .fetch()
    }

    override fun findById(id: Long): CommunityListRes? {
        return queryFactory
            .select(communityRes())
            .from(community)
            .innerJoin(member)
            .on(community.memberId.eq(member.id))
            .fetchFirst()
    }

    fun communityRes(): ConstructorExpression<CommunityListRes> {
        return Projections.constructor(
            CommunityListRes::class.java,
            community.id,
            community.content,
            community.createdAt,
            member.name
        )
    }
}

//    private ConstructorExpression<StoryQueryByMemberResponse> q