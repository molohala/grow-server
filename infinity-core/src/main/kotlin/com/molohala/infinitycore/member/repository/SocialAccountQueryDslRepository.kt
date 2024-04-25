package com.molohala.infinitycore.member.repository

import com.molohala.infinitycore.member.domain.consts.SocialType
import com.molohala.infinitycore.member.domain.entity.QMember.member
import com.molohala.infinitycore.member.domain.entity.SocialAccount
import com.querydsl.jpa.impl.JPAQueryFactory
import com.molohala.infinitycore.member.domain.entity.QSocialAccount.socialAccount
import com.molohala.infinitycore.rank.domain.dto.RedisSocialAccount
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

    override fun getSocialAccountsWithMemberInfo(): List<RedisSocialAccount> {
        return queryFactory
            .select(
                Projections.constructor(
                    RedisSocialAccount::class.java,
                    socialAccount.memberId,
                    member.name,
                    member.email,
                    socialAccount.socialId,
                    socialAccount.socialType
                )
            )
            .from(socialAccount)
            .innerJoin(member)
            .fetch()
    }

    override fun getSocialAccountsWithMemberInfo(socialType: SocialType): List<RedisSocialAccount> {
        return queryFactory
            .select(
                Projections.constructor(
                    RedisSocialAccount::class.java,
                    socialAccount.memberId,
                    member.name,
                    member.email,
                    socialAccount.socialId,
                    socialAccount.socialType
                )
            )
            .from(socialAccount)
            .where(socialAccount.socialType.eq(socialType))
            .innerJoin(member).on(socialAccount.memberId.eq(member.id))
            .fetch()
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