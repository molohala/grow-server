package com.molohala.infinitycore.member.repository

import com.molohala.infinitycore.member.domain.consts.SocialType
import com.molohala.infinitycore.member.domain.entity.SocialAccount
import com.querydsl.jpa.impl.JPAQueryFactory
import com.molohala.infinitycore.member.domain.entity.QSocialAccount.socialAccount
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Repository

@Repository
class SocialAccountQueryDslRepository(
    private val queryFactory: JPAQueryFactory
) : SocialAccountQueryRepository {
    override fun findSocialAccountByMemberIdAndType(memberId: Long, type: SocialType): SocialAccount? {
        return queryFactory
            .select(socialAccountProjection())
            .from(socialAccount)
            .where(socialAccount.memberId.eq(memberId).and(socialAccount.socialType.eq(type)))
            .fetchOne()
    }

    private fun socialAccountProjection() = Projections.constructor(
        SocialAccount::class.java,
        socialAccount.socialId,
        socialAccount.socialType,
        socialAccount.memberId,
        socialAccount.id,
        socialAccount.createdAt
    )
}