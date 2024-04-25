package com.molohala.infinitycore.member.domain.entity

import com.molohala.infinitycore.member.domain.consts.SocialType
import jakarta.persistence.*
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable
import java.time.LocalDateTime

@RedisHash("socials")
class RedisSocialAccount(
    @Column(nullable = false)
    val socialId: String,

    @field:Enumerated(EnumType.STRING)
    @field:Column(nullable = false)
    val socialType: SocialType,

    @Column(name = "fk_member_id", nullable = false)
    val memberId: Long,

    // for projection
    val id: Long? = null,
    val createdAt: LocalDateTime? = null,
) : Serializable {
    fun to() = SocialAccount(socialId, socialType, memberId, id, createdAt)

    companion object {
        fun from(item: SocialAccount) = RedisSocialAccount(
            item.socialId,
            item.socialType,
            item.memberId,
            item.id,
            item.createdAt
        )
    }
}