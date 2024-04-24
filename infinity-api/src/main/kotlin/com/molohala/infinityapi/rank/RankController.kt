package com.molohala.infinityapi.rank

import com.molohala.infinityapi.response.ResponseData
import com.molohala.infinitycore.rank.application.service.RankService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rank")
class RankController(
    val rankService: RankService
) {
    @GetMapping("/github/total")
    fun githubTotal() = ResponseData.ok("전체 랭킹 조회 완료", rankService.getGithubTotalRanking())

    @GetMapping("/github/week")
    fun githubWeek() = ResponseData.ok("주간 랭킹 조회 완료", rankService.getGithubWeekRanking())

    @GetMapping("/github/today")
    fun githubToday() = ResponseData.ok("일간 랭킹 조회 완료", rankService.getGithubTodayRanking())
}