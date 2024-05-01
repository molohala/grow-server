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
    fun githubTotal() = ResponseData.ok("깃허브 전체 랭킹 조회 완료", rankService.getGithubTotalRanking())

    @GetMapping("/github/week")
    fun githubWeek() = ResponseData.ok("깃허브 주간 랭킹 조회 완료", rankService.getGithubWeekRanking())

    @GetMapping("/github/today")
    fun githubToday() = ResponseData.ok("깃허브 일간 랭킹 조회 완료", rankService.getGithubTodayRanking())

    @GetMapping("/solvedac/total")
    fun solvedAcTotal() = ResponseData.ok("솔브드 전체 랭킹 조회 완료", rankService.getSolvedAcTotalRanking())

    @GetMapping("/solvedac/week")
    fun solvedAcWeek() = ResponseData.ok("솔브드 주간 랭킹 조회 완료", rankService.getSolvedAcWeekRanking())

    @GetMapping("/solvedac/today")
    fun solvedAcToday() = ResponseData.ok("솔브드 일간 랭킹 조회 완료", rankService.getSolvedAcTodayRanking())
}