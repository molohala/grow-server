package com.molohala.grow.core.block.application.dto.res

import com.molohala.grow.core.member.domain.entity.Member

data class BlockRes(
    val id: Long,
    val blockedUser: Member,
)
