package com.molohala.infinitycore.community.application.service

import com.molohala.infinitycore.common.PageRequest
import com.molohala.infinitycore.community.application.dto.req.CommunityModifyReq
import com.molohala.infinitycore.community.application.dto.req.CommunitySaveReq
import com.molohala.infinitycore.community.application.dto.res.CommunityListRes
import com.molohala.infinitycore.community.domain.consts.CommunityState
import com.molohala.infinitycore.community.domain.entity.Community
import com.molohala.infinitycore.community.domain.exception.CommunityNotFoundException
import com.molohala.infinitycore.community.repository.CommunityJpaRepository
import com.molohala.infinitycore.community.repository.QueryCommunityRepository
import com.molohala.infinitycore.member.application.MemberSessionHolder
import com.molohala.infinitycore.member.domain.exception.AccessDeniedException
import com.molohala.infinitycore.member.repository.MemberJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommunityService(
    private val communityJpaRepository: CommunityJpaRepository,
    private val queryCommunityRepository: QueryCommunityRepository,
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

    fun getList(page: PageRequest) =
        queryCommunityRepository.findWithPagination(page)

    fun getById(id: Long) = queryCommunityRepository.findById(id)

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

    }
}