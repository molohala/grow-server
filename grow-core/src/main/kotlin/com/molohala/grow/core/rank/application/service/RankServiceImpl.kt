package com.molohala.grow.core.rank.application.service

import com.molohala.grow.core.block.repository.BlockRepository
import com.molohala.grow.core.member.application.MemberSessionHolder
import com.molohala.grow.core.rank.domain.dto.RedisSocialAccount
import com.molohala.grow.core.rank.domain.dto.res.RankingRes
import com.molohala.grow.core.rank.domain.dto.res.RankingTimedListRes
import jakarta.transaction.Transactional
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Service
class RankServiceImpl(
    private val redisTemplate: RedisTemplate<String, RedisSocialAccount>,
    private val blockRepository: BlockRepository,
    private val memberSessionHolder: MemberSessionHolder
) : RankService {
    @Transactional(rollbackOn = [Exception::class])
    override fun getGithubTotalRanking(): RankingTimedListRes {
        return getRankForKey("github:total")
    }

    @Transactional(rollbackOn = [Exception::class])
    override fun getGithubWeekRanking(): RankingTimedListRes {
        return getRankForKey("github:week")
    }

    @Transactional(rollbackOn = [Exception::class])
    override fun getGithubTodayRanking(): RankingTimedListRes {
        return getRankForKey("github:today")
    }

    override fun getSolvedAcTotalRanking(): RankingTimedListRes {
        return getRankForKey("solvedac:total")
    }

    override fun getSolvedAcWeekRanking(): RankingTimedListRes {
        return getRankForKey("solvedac:week")
    }

    override fun getSolvedAcTodayRanking(): RankingTimedListRes {
        return getRankForKey("solvedac:today")
    }

    @Suppress("SameParameterValue")
    private fun getRankForKey(key: String): RankingTimedListRes {
        val zSet = redisTemplate.opsForZSet()
        val member = memberSessionHolder.current()
        val blocks = blockRepository.findByUserId(member.id!!).associate { it.blockedUserId to Unit /* inspired by java's internal hash set implementation */ } //.map { it.blockedUserId }.toSet()

        val lastNano = 2L * 60L * 60L * 1_000L * 1_000_000L - redisTemplate.getExpire(key, TimeUnit.NANOSECONDS)

        var rank = 0
//        var keepCount = 0
//        var prevScore = -1L
        val map = zSet.reverseRangeWithScores(key, 0, -1)!!
            .map {
                val value = it.value!!
                val score = it.score!!.toLong()
                rank++
//                if (prevScore != score) { // 공동등수
//                    rank += 1 + keepCount
//                    keepCount = 0
//                } else {
//                    keepCount++
//                }
//                prevScore = score
                RankingRes(value.memberId, value.name, value.socialId, rank, blocks.contains(value.memberId), score)
            }

        return RankingTimedListRes(LocalDateTime.now().minusNanos(lastNano), map)
    }
}
