package com.molohala.infinitycore.community.application.service

import com.molohala.infinitycore.community.application.dto.req.CommunitySaveReq
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommunityService {

    @Transactional(rollbackFor = [Exception::class])
    fun save(communitySaveReq: CommunitySaveReq){

    }
}