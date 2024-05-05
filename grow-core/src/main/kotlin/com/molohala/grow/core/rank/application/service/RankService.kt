package com.molohala.grow.core.rank.application.service

import com.molohala.grow.core.rank.domain.dto.res.RankingRes

interface RankService {
    fun getGithubTotalRanking(): List<RankingRes>
    fun getGithubWeekRanking(): List<RankingRes>
    fun getGithubTodayRanking(): List<RankingRes>

    fun getSolvedAcTotalRanking(): List<RankingRes>
    fun getSolvedAcWeekRanking(): List<RankingRes>
    fun getSolvedAcTodayRanking(): List<RankingRes>
}