package com.molohala.infinityapi.community

import com.molohala.infinityapi.response.Response
import com.molohala.infinityapi.response.ResponseData
import com.molohala.infinitycore.community.application.dto.req.CommunitySaveReq
import com.molohala.infinitycore.community.application.service.CommunityService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("community")
class CommunityController(
    private val communityService: CommunityService
) {
    @PostMapping
    fun save(
        @RequestBody communitySaveReq: CommunitySaveReq
    ): Response {
        communityService.save(communitySaveReq)
        return Response.ok("커뮤니티 생성 성공")
    }

}