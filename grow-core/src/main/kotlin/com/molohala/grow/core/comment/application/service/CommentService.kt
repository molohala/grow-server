package com.molohala.grow.core.comment.application.service

import com.molohala.grow.core.comment.application.dto.req.CommentModifyReq
import com.molohala.grow.core.comment.application.dto.req.CommentReq
import com.molohala.grow.core.comment.domain.consts.CommentState
import com.molohala.grow.core.comment.domain.entity.Comment
import com.molohala.grow.core.comment.domain.exception.CommentNotFoundException
import com.molohala.grow.core.comment.repository.CommentJpaRepository
import com.molohala.grow.core.comment.repository.QueryCommentRepository
import com.molohala.grow.core.member.application.MemberSessionHolder
import com.molohala.grow.core.member.domain.entity.Member
import com.molohala.grow.core.member.domain.exception.AccessDeniedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommentService(
    private val commentJpaRepository: CommentJpaRepository,
    private val queryCommentRepository: QueryCommentRepository,
    private val memberSessionHolder: MemberSessionHolder
) {

    @Transactional(rollbackFor = [Exception::class])
    fun save(commentReq: CommentReq){
        val member: Member = memberSessionHolder.current()
        commentJpaRepository.save(
            Comment(
                commentReq.content,
                CommentState.ACTIVE,
                member.id!!,
                commentReq.communityId
            )
        )
    }

    fun get(communityId: Long)=
        queryCommentRepository.findByCommunityId(communityId)?:
        throw CommentNotFoundException()

    @Transactional(rollbackFor = [Exception::class])
    fun delete(commentId: Long){
        val member: Member = memberSessionHolder.current()
        val comment: Comment = commentJpaRepository.findByIdOrNull(commentId)?:
        throw CommentNotFoundException()
        validateCommand(member, comment)
        comment.delete()
    }

    @Transactional(rollbackFor = [Exception::class])
    fun modify(commentModifyReq: CommentModifyReq){
        val member: Member = memberSessionHolder.current()
        val comment: Comment = commentJpaRepository
            .findByIdOrNull(commentModifyReq.commentId)?:
            throw CommentNotFoundException()
        validateCommand(member, comment)
        comment.modify(commentModifyReq.content)
    }

    private fun validateCommand(member:Member, comment: Comment){
        if(member.id!=comment.memberId){
            throw AccessDeniedException()
        }
    }
}