package com.molohala.infinityapi.community

import com.molohala.infinityapi.response.Response
import com.molohala.infinityapi.response.ResponseData
import com.molohala.infinitycore.common.PageRequest
import com.molohala.infinitycore.community.application.dto.req.CommunityModifyReq
import com.molohala.infinitycore.community.application.dto.req.CommunitySaveReq
import com.molohala.infinitycore.community.application.service.CommunityService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/community")
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

    @GetMapping
    fun getList(
        @ModelAttribute pageRequest: PageRequest
    ) = ResponseData.ok("커뮤티니 조회 성공",communityService.getList(pageRequest))

    @GetMapping("/{id}")
    fun getById(
        @PathVariable("id") id: Long
    ) = ResponseData.ok("커뮤니티 조회 성공", communityService.getById(id))

    @PatchMapping
    fun modify(
        @RequestBody communityModifyReq: CommunityModifyReq
    ): Response {
        communityService.modify(communityModifyReq)
        return Response.ok("커뮤니티 수정 성공")
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: Long
    ): Response {
        communityService.delete(id)
        return Response.ok("커뮤니티 삭제 성공")
    }
}