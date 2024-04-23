package com.molohala.infinitycore.rank.application.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RankServiceImpl(
    val redisTemplate: RedisTemplate<*, *>
) : RankService {
    override fun getGithubTotalRanking() {
        val zSetOp = redisTemplate.opsForZSet()

    }
}