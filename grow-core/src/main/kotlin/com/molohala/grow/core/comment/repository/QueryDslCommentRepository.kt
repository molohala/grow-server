package com.molohala.grow.core.comment.repository

import com.molohala.grow.core.comment.application.dto.res.CommentRes
import com.molohala.grow.core.comment.domain.consts.CommentState
import com.molohala.grow.core.comment.domain.entity.QComment.comment
import com.molohala.grow.core.member.domain.entity.QMember.member
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class QueryDslCommentRepository(
    private val queryFactory: JPAQueryFactory
) : QueryCommentRepository {
    override fun findByCommunityId(communityId: Long): List<CommentRes>? {
        return queryFactory.select(commentProjection())
            .from(comment)
            .where(comment.communityId.eq(communityId).and(comment.commentState.eq(CommentState.ACTIVE)))
            .orderBy(comment.createdAt.desc())
            .innerJoin(member)
            .on(comment.memberId.eq(member.id))
            .fetch()
    }

    override fun findRecentComment(communityId: Long): CommentRes? {
        return queryFactory.select(commentProjection())
            .from(comment)
            .where(comment.communityId.eq(communityId).and(comment.commentState.eq(CommentState.ACTIVE)))
            .orderBy(comment.createdAt.desc())
            .innerJoin(member)
            .on(comment.memberId.eq(member.id))
            .fetchFirst()
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
