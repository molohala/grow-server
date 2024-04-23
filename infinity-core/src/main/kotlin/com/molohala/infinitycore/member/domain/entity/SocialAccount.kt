package com.molohala.infinitycore.member.domain.entity

import com.molohala.infinitycore.common.BaseIdAndTimeEntity
import com.molohala.infinitycore.member.domain.consts.SocialType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "tbl_social_account")
class SocialAccount(
    @Column(nullable = false)
    val socialId: String,

    @field:Enumerated(EnumType.STRING)
    @field:Column(nullable = false)
    val socialType: SocialType,

    @Column(name = "fk_member_id", nullable = false)
    val memberId: Long,

    // for projection
    id: Long? = null,
    createdAt: LocalDateTime? = null,
) : BaseIdAndTimeEntity(id, createdAt)