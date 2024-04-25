package com.molohala.infinitycore.member.repository

import com.molohala.infinitycore.member.domain.consts.SocialType
import com.molohala.infinitycore.member.domain.entity.SocialAccount
import com.molohala.infinitycore.rank.domain.dto.RedisSocialAccount

interface SocialAccountQueryRepository {
    fun findSocialAccountByMemberIdAndType(memberId: Long, type: SocialType): SocialAccount?
    fun getSocialAccountsWithMemberInfo(): List<RedisSocialAccount>
    fun getSocialAccountsWithMemberInfo(socialType: SocialType): List<RedisSocialAccount>
}