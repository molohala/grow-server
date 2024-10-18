package com.molohala.grow.core.rank.application.service

import com.molohala.grow.core.rank.domain.dto.res.RankingTimedListRes

interface RankService {
    fun getGithubTotalRanking(): RankingTimedListRes
    fun getGithubWeekRanking(): RankingTimedListRes
    fun getGithubTodayRanking(): RankingTimedListRes

    fun getSolvedAcTotalRanking(): RankingTimedListRes
    fun getSolvedAcWeekRanking(): RankingTimedListRes
    fun getSolvedAcTodayRanking(): RankingTimedListRes
}
