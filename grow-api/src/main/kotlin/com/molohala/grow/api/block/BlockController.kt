package com.molohala.grow.api.block

import com.molohala.grow.api.response.Response
import com.molohala.grow.api.response.ResponseData
import com.molohala.grow.core.block.service.BlockService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/block")
class BlockController(
    private val blockService: BlockService
) {

    @GetMapping
    fun getAll() =
        ResponseData.ok("차단한 유저 조회 성공", blockService.getAll())

    @PostMapping("/{blockUserId}")
    fun block(
        @PathVariable("blockUserId") blockUserId: Long
    ): Response {
        blockService.blockUser(blockUserId)
        return Response.ok("유저 차단 성공")
    }

    @DeleteMapping("/allow/{blockUserId}")
    fun allow(
        @PathVariable("blockUserId") blockUserId: Long
    ): Response {
        blockService.allowUser(blockUserId)
        return Response.ok("유저 차단 해제 성공")
    }
}