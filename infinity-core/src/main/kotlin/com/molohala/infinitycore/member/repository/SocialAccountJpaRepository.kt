package com.molohala.infinitycore.member.repository

import com.molohala.infinitycore.member.domain.entity.SocialAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SocialAccountJpaRepository : JpaRepository<SocialAccount, Long> {
    fun findSocialAccountsByMemberId(memberId: Long): List<SocialAccount>
}