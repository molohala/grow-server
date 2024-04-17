package com.molohala.infinityapi.info

import com.molohala.infinityapi.response.ResponseData
import com.molohala.infinitycore.info.application.service.InfoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
}