package com.molohala.grow.core.member.repository

import com.molohala.grow.core.block.domain.entity.QBlock.block
import com.molohala.grow.core.member.domain.entity.Member
import com.molohala.grow.core.member.domain.entity.QMember.member
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class QueryDslMemberRepository(
    private val queryFactory: JPAQueryFactory
) : QueryMemberRepository {

    override fun findAll(userId: Long): List<Member> {
        return queryFactory.select(member)
            .from(member)
            .innerJoin(block).on(block.userId.eq(userId).and(block.blockedUserId.eq(member.id)))
            .orderBy(block.createdAt.desc())
            .fetch()
    }
}