package com.molohala.infinityapi.info

import com.molohala.infinityapi.response.Response
import com.molohala.infinityapi.response.ResponseData
import com.molohala.infinitycore.info.application.dto.req.NewSocialAccountReq
import com.molohala.infinitycore.info.application.service.InfoService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/info")
class InfoController(
    private val infoService: InfoService
) {
    @GetMapping("/github")
    fun githubInfo(@RequestParam("name") name: String)
        = ResponseData.ok("깃허브 프로필 조회 완료", infoService.getGithubInfo(name))

    @GetMapping("/solvedac")
    fun solvedAcInfo(@RequestParam("name") name: String)
        = ResponseData.ok("솔브드 프로필 조회 완료", infoService.getSolvedAcInfo(name))

    @GetMapping("/me")
    fun getMyUserInfo()
        = ResponseData.ok("내 정보 조회 완료", infoService.getMyInfo())

    @PostMapping("/github")
    fun uploadGithubInfo(@RequestBody @Valid data: NewSocialAccountReq): Response {
        infoService.submitGithubId(data.socialId)
        return Response.ok("깃헙 아이디 생성/갱신 완료")
    }

    @PostMapping("/solvedac")
    fun uploadSolvedAcInfo(@RequestBody @Valid data: NewSocialAccountReq): Response {
        infoService.submitSolvedAcInfo(data.socialId)
        return Response.ok("솔브드 아이디 생성/갱신 완료")
    }
}