package com.molohala.infinityapi.like

import com.molohala.infinityapi.response.Response
import com.molohala.infinitycore.like.application.service.LikeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/like")
class LikeController(
    private val likeService: LikeService
) {
    @PatchMapping("{id}")
    fun patch(
        @PathVariable("id") id: Long
    ): Response {
        likeService.patch(id)
        return Response.ok("좋아요/취소 성공")
    }

    @GetMapping("/{id}")
    fun cnt(
        @PathVariable("id") id: Long
    )= likeService.getCnt(id)
}