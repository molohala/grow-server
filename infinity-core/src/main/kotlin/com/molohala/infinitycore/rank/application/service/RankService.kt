package com.molohala.infinitycore.rank.application.service

import com.molohala.infinitycore.rank.domain.dto.res.GithubRankingRes

interface RankService {
    fun getGithubTotalRanking(): List<GithubRankingRes>
    fun getGithubWeekRanking(): List<GithubRankingRes>
    fun getGithubTodayRanking(): List<GithubRankingRes>
}