package com.molohala.infinitycore.community.application.service

import com.molohala.infinitycommon.exception.GlobalExceptionCode
import com.molohala.infinitycommon.exception.custom.CustomException
import com.molohala.infinitycore.common.PageRequest
import com.molohala.infinitycore.community.application.dto.req.CommunityModifyReq
import com.molohala.infinitycore.community.application.dto.req.CommunitySaveReq
import com.molohala.infinitycore.community.application.dto.res.CommunityRes
import com.molohala.infinitycore.community.domain.consts.CommunityState
import com.molohala.infinitycore.community.domain.entity.Community
import com.molohala.infinitycore.community.domain.exception.CommunityNotFoundException
import com.molohala.infinitycore.community.repository.CommunityJpaRepository
import com.molohala.infinitycore.community.repository.QueryCommunityRepository
import com.molohala.infinitycore.like.repository.QueryLikeRepository
import com.molohala.infinitycore.member.application.MemberSessionHolder
import com.molohala.infinitycore.member.domain.entity.Member
import com.molohala.infinitycore.member.domain.exception.AccessDeniedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommunityService(
    private val communityJpaRepository: CommunityJpaRepository,
    private val queryCommunityRepository: QueryCommunityRepository,
    private val queryLikeRepository: QueryLikeRepository,
    private val memberSessionHolder: MemberSessionHolder
) {

    @Transactional(rollbackFor = [Exception::class])
    fun save(communitySaveReq: CommunitySaveReq){
        communityJpaRepository.save(
            Community(
                communitySaveReq.content,
                CommunityState.ACTIVE,
                memberSessionHolder.current().id!!
            )
        )
    }

    fun getList(page: PageRequest): List<CommunityRes> {
        if (page.page < 1) throw CustomException(GlobalExceptionCode.INVALID_PARAMETER)
        return queryCommunityRepository.findWithPagination(page)
            .map {
                CommunityRes(
                    it.communityId,
                    it.content,
                    it.createdAt,
                    queryLikeRepository.getCntByCommunityId(it.communityId),
                    queryLikeRepository.existsByCommunityIdAndMemberId(it.communityId,it.writerId),
                    it.writerName,
                    it.writerId
                )
            }
    }

    fun getById(id: Long): CommunityRes? {
        val member: Member = memberSessionHolder.current()
        val likeCnt: Long = queryLikeRepository.getCntByCommunityId(id)
        val isLike: Boolean = queryLikeRepository
            .existsByCommunityIdAndMemberId(id,member.id!!)
        return queryCommunityRepository.findById(id, likeCnt, isLike)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun modify(communityModifyReq: CommunityModifyReq) {
        val community = communityJpaRepository
            .findByIdOrNull(communityModifyReq.id)?: throw CommunityNotFoundException()
        val curMember = memberSessionHolder.current()
        if(curMember.id != community.id){
            throw AccessDeniedException()
        }
        community.modify(communityModifyReq.content)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun delete(id: Long) {
        val community = communityJpaRepository
            .findByIdOrNull(id)?: throw CommunityNotFoundException()
        val curMember = memberSessionHolder.current()
        if(curMember.id != community.id){
            throw AccessDeniedException()
        }
        community.delete()
    }
}