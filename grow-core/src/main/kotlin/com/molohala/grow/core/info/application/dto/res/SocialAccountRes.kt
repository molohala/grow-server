package com.molohala.grow.core.info.application.dto.res

import com.molohala.grow.core.member.domain.consts.SocialType

data class SocialAccountRes(
    val socialId: String,
    val socialType: SocialType,
)