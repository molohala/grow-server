package com.molohala.grow.core.comment.repository

import com.molohala.grow.core.block.domain.entity.QBlock.block
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
    override fun findByCommunityId(communityId: Long, userId: Long): List<CommentRes>? {
        return queryFactory.select(commentProjection())
            .from(comment)
            .where(comment.communityId.eq(communityId).and(comment.commentState.eq(CommentState.ACTIVE)))
            .innerJoin(member).on(comment.memberId.eq(member.id))
            .leftJoin(block).on(block.blockedUserId.eq(comment.memberId).and(block.userId.eq(userId)))
            .where(block.id.isNull) // 차단된 유저의 댓글은 block.id가 null이 아니므로 필터링됨
            .orderBy(comment.createdAt.desc())
            .fetch()
    }

    override fun findRecentComment(communityId: Long, userId: Long): CommentRes? {
        return queryFactory.select(commentProjection())
            .from(comment)
            .where(comment.communityId.eq(communityId).and(comment.commentState.eq(CommentState.ACTIVE)))
            .innerJoin(member).on(comment.memberId.eq(member.id))
            .leftJoin(block).on(block.blockedUserId.eq(comment.memberId).and(block.userId.eq(userId)))
            .where(block.id.isNull)
            .orderBy(comment.createdAt.desc())
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
