package com.molohala.infinityapi.comment

import com.molohala.infinityapi.response.Response
import com.molohala.infinityapi.response.ResponseData
import com.molohala.infinitycore.comment.application.dto.req.CommentModifyReq
import com.molohala.infinitycore.comment.application.dto.req.CommentReq
import com.molohala.infinitycore.comment.application.service.CommentService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/comment")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping
    fun save(req: CommentReq): Response {
        commentService.save(req)
        return Response.ok("댓글 작성 성공")
    }

    @GetMapping
    fun get(@RequestParam("communityId") communityId:Long) =
        ResponseData.ok("댓글 조회 성공",commentService.get(communityId))

    @PatchMapping
    fun modify(req: CommentModifyReq) =
        ResponseData.ok("댓글 수정 성공",commentService.modify(req))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Response {
        commentService.delete(id)
        return Response.ok("댓글 삭제 성공")
    }
}