package com.molohala.infinitycore.comment.repository

import com.molohala.infinitycore.comment.application.dto.res.CommentRes
import com.molohala.infinitycore.comment.domain.entity.QComment.comment
import com.molohala.infinitycore.member.domain.entity.QMember.member
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class QueryDslCommentRepository(
    private val queryFactory: JPAQueryFactory
): QueryCommentRepository {
    override fun findByNotificationId(communityId: Long): List<CommentRes>? {
        return queryFactory.select(commentProjection())
            .from(comment)
            .where(comment.communityId.eq(communityId))
            .innerJoin(member)
            .on(comment.memberId.eq(member.id))
            .fetch()
    }


    private fun commentProjection(): ConstructorExpression<CommentRes> {
        return Projections.constructor(
            CommentRes::class.java,
            comment.id,
            comment.content,
            comment.createdAt,
            member.id,
            member.name
        )
    }
}