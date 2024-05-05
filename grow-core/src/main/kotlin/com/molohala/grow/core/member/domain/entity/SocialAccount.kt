package com.molohala.grow.core.member.domain.entity

import com.molohala.grow.core.common.BaseIdAndTimeEntity
import com.molohala.grow.core.member.domain.consts.SocialType
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