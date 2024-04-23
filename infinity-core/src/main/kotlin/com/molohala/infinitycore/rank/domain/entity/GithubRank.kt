package com.molohala.infinitycore.rank.domain.entity

import com.molohala.infinitycore.common.BaseTimeEntity
import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash("githubRanking")
class GithubRank(
    val memberId: Long,

    @Indexed
    val totalCommits: Long,

    @Indexed
    val lastWeekCommits: Long,

    @Indexed
    val todayCommits: Long,


    @Id
    val id: String? = null,
) : BaseTimeEntity(null)