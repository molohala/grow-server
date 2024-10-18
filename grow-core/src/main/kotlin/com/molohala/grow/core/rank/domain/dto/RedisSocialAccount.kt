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
    val additionalInfo: MutableMap<String, Any> = mutableMapOf()
) : Serializable

class SocialAccountDTO(
    private val memberId: Long,
    private val name: String,
    private val email: String,
    private val socialId: String,
    private val socialType: SocialType,
) : Serializable {
    fun toRedis() = RedisSocialAccount(
        memberId = memberId,
        name = name,
        email = email,
        socialId = socialId,
        socialType = socialType,
    )
}