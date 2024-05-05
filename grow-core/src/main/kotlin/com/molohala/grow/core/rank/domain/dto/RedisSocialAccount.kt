package com.molohala.grow.core.rank.domain.dto

import com.molohala.grow.core.member.domain.consts.SocialType
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@Suppress("unused")
@RedisHash("socials")
class RedisSocialAccount(
    val memberId: Long,
    val name: String,
    val email: String,
    val socialId: String,
    val socialType: SocialType,
) : Serializable