package com.molohala.grow.core.member.repository

import com.molohala.grow.core.member.domain.consts.SocialType
import com.molohala.grow.core.member.domain.entity.SocialAccount
import com.molohala.grow.core.rank.domain.dto.RedisSocialAccount

interface SocialAccountQueryRepository {
    fun findSocialAccountByMemberIdAndType(memberId: Long, type: SocialType): SocialAccount?
    fun getSocialAccountsWithMemberInfo(): List<RedisSocialAccount>
    fun getSocialAccountsWithMemberInfo(socialType: SocialType): List<RedisSocialAccount>
}