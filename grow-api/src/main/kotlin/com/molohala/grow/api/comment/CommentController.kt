package com.molohala.grow.api.comment

import com.molohala.grow.api.response.Response
import com.molohala.grow.api.response.ResponseData
import com.molohala.grow.core.comment.application.dto.req.CommentModifyReq
import com.molohala.grow.core.comment.application.dto.req.CommentReq
import com.molohala.grow.core.comment.application.service.CommentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comment")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping
    fun save(@RequestBody req: CommentReq): Response {
        commentService.save(req)
        return Response.ok("댓글 작성 성공")
    }

    @GetMapping
    fun get(@RequestParam("communityId") communityId:Long) =
        ResponseData.ok("댓글 조회 성공",commentService.get(communityId))

    @PatchMapping
    fun modify(@RequestBody req: CommentModifyReq): Response {
        commentService.modify(req)
        return Response.ok("댓글 수정 성공")
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Response {
        commentService.delete(id)
        return Response.ok("댓글 삭제 성공")
    }
}