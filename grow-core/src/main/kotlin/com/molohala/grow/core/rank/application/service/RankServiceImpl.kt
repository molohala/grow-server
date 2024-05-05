package com.molohala.grow.core.rank.application.service

import com.molohala.grow.core.rank.domain.dto.RedisSocialAccount
import com.molohala.grow.core.rank.domain.dto.res.RankingRes
import jakarta.transaction.Transactional
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RankServiceImpl(
    val redisTemplate: RedisTemplate<String, RedisSocialAccount>
) : RankService {
    @Transactional(rollbackOn = [Exception::class])
    override fun getGithubTotalRanking(): List<RankingRes> {
        return getRankForKey("githubTotal")
    }

    @Transactional(rollbackOn = [Exception::class])
    override fun getGithubWeekRanking(): List<RankingRes> {
        return getRankForKey("githubWeek")
    }

    @Transactional(rollbackOn = [Exception::class])
    override fun getGithubTodayRanking(): List<RankingRes> {
        return getRankForKey("githubToday")
    }

    override fun getSolvedAcTotalRanking(): List<RankingRes> {
        return getRankForKey("solvedAcTotal")
    }

    override fun getSolvedAcWeekRanking(): List<RankingRes> {
        return getRankForKey("solvedAcWeek")
    }

    override fun getSolvedAcTodayRanking(): List<RankingRes> {
        return getRankForKey("solvedAcToday")
    }

    @Suppress("SameParameterValue")
    private fun getRankForKey(key: String): List<RankingRes> {
        val zSet = redisTemplate.opsForZSet()

        var rank = 0
        var keepCount = 0
        var prevScore = -1L
        val map = zSet.reverseRangeWithScores(key, 0, -1)!!.map {
            val value = it.value!!
            val score = it.score!!.toLong()
            if (prevScore != score) {
                rank += 1 + keepCount
                keepCount = 0
            } else {
                keepCount++
            }
            prevScore = score
            RankingRes(value.memberId, value.name, value.socialId, rank, score)
        }
        return map
    }
}