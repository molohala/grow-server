package com.molohala.infinitycore.community.application.service

import com.molohala.infinitycore.community.application.dto.req.CommunitySaveReq
import com.molohala.infinitycore.community.domain.entity.Community
import com.molohala.infinitycore.community.repository.CommunityJpaRepository
import com.molohala.infinitycore.member.application.MemberSessionHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommunityService(
    private val communityJpaRepository: CommunityJpaRepository,
    private val memberSessionHolder: MemberSessionHolder
) {

    @Transactional(rollbackFor = [Exception::class])
    fun save(communitySaveReq: CommunitySaveReq){
        communityJpaRepository.save(
            Community(
                communitySaveReq.content,
                memberSessionHolder.current().id!!
            )
        )
    }

}