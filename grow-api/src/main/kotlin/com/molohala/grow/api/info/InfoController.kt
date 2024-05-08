package com.molohala.grow.api.info

import com.molohala.grow.api.response.Response
import com.molohala.grow.api.response.ResponseData
import com.molohala.grow.core.info.application.dto.req.EditUserInfoReq
import com.molohala.grow.core.info.application.dto.req.NewSocialAccountReq
import com.molohala.grow.core.info.application.service.InfoService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/info")
class InfoController(
    private val infoService: InfoService
) {
    @GetMapping("/github")
    fun githubInfo(@RequestParam("name") name: String) =
        ResponseData.ok("깃허브 프로필 조회 완료", infoService.getGithubInfo(name))

    @GetMapping("/solvedac")
    fun solvedAcInfo(@RequestParam("name") name: String) =
        ResponseData.ok("솔브드 프로필 조회 완료", infoService.getSolvedAcInfo(name))

    @GetMapping("/me")
    fun getMyUserInfo() = ResponseData.ok("내 정보 조회 완료", infoService.getMyInfo())

    @PatchMapping("/me")
    fun patchMyInfo(@RequestBody data: EditUserInfoReq): Response {
        infoService.editInfo(data.bio, data.job)
        return Response.ok("내 정보 수정 완료")
    }

    @GetMapping("/user/{id}")
    fun getOtherUserInfo(@PathVariable id: Long) = ResponseData.ok("유저 정보 조회 완료", infoService.getUserInfo(id))

    @PostMapping("/github")
    fun uploadGithubInfo(@RequestBody @Valid data: NewSocialAccountReq): Response {
        infoService.submitGithubId(data.socialId)
        return Response.ok("깃허브 아이디 생성/갱신 완료")
    }

    @PostMapping("/solvedac")
    fun uploadSolvedAcInfo(@RequestBody @Valid data: NewSocialAccountReq): Response {
        infoService.submitSolvedAcInfo(data.socialId)
        return Response.ok("솔브드 아이디 생성/갱신 완료")
    }
}