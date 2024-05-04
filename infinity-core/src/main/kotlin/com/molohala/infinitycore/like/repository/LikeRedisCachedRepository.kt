package com.molohala.infinitycore.like.repository

import com.molohala.infinitycore.like.domain.dto.LikeCount
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class LikeRedisCachedRepository(
    private val likeRedisTemplate: RedisTemplate<String, Long>
) : LikeCachedRepository {
    override fun cache(communityId: Long, likedCount: Long) {
        val zSet = likeRedisTemplate.opsForZSet()

        zSet.remove("community:likes", communityId)
        zSet.add("community:likes", communityId, likedCount.toDouble())
    }

    override fun clear(communityId: Long) {
        val zSet = likeRedisTemplate.opsForZSet()

        zSet.remove("community:likes", communityId)
    }

    override fun getAll(count: Long): List<LikeCount> {
        val zSet = likeRedisTemplate.opsForZSet()

        return zSet.reverseRangeWithScores("community:likes", 0, count - 1)!!.map {
            LikeCount(it.value!!, it.score!!.toLong())
        }
    }
}