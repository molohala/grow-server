package com.molohala.infinitycore.member.repository

import com.molohala.infinitycore.member.domain.consts.SocialType
import com.molohala.infinitycore.member.domain.entity.SocialAccount

interface SocialAccountQueryRepository {
    fun findSocialAccountByMemberIdAndType(memberId: Long, type: SocialType): SocialAccount?
}