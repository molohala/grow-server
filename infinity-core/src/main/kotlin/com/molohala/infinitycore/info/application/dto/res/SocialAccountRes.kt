package com.molohala.infinitycore.info.application.dto.res

import com.molohala.infinitycore.member.domain.consts.SocialType

data class SocialAccountRes(
    val socialId: String,
    val socialType: SocialType,
)