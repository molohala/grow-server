package com.molohala.infinitycore.rank.application.service

import com.molohala.infinitycore.rank.domain.dto.RedisSocialAccount
import com.molohala.infinitycore.rank.domain.dto.res.GithubRankingRes
import jakarta.transaction.Transactional
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RankServiceImpl(
    val redisTemplate: RedisTemplate<String, RedisSocialAccount>
) : RankService {
    @Transactional(rollbackOn = [Exception::class])
    override fun getGithubTotalRanking(): List<GithubRankingRes> {
        return getRankForKey("githubTotal", 10)
    }

    @Transactional(rollbackOn = [Exception::class])
    override fun getGithubWeekRanking(): List<GithubRankingRes> {
        return getRankForKey("githubWeek", 10)
    }

    @Transactional(rollbackOn = [Exception::class])
    override fun getGithubTodayRanking(): List<GithubRankingRes> {
        return getRankForKey("githubToday", 10)
    }

    @Suppress("SameParameterValue")
    private fun getRankForKey(key: String, size: Long): List<GithubRankingRes> {
        val zSet = redisTemplate.opsForZSet()

        var rank = 0
        var keepCount = 0
        var prevScore = -1L
        val map = zSet.reverseRangeWithScores(key, 0, size)!!.map {
            val value = it.value!!
            val score = it.score!!.toLong()
            if (prevScore != score) {
                rank += 1 + keepCount
                keepCount = 0
            } else {
                keepCount++
            }
            prevScore = score
            GithubRankingRes(value.memberId, value.name, value.socialId, rank, score)
        }
        return map
    }
}