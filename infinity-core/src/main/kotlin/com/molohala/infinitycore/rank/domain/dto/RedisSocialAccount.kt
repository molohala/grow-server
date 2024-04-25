package com.molohala.infinitycore.rank.domain.dto

import com.molohala.infinitycore.member.domain.consts.SocialType
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("socials")
class RedisSocialAccount(
    val memberId: Long,
    val name: String,
    val email: String,
    val socialId: String,
    val socialType: SocialType,
) : Serializable