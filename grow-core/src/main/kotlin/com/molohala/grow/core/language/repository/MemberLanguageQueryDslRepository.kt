package com.molohala.grow.core.language.repository

import com.molohala.grow.core.language.domain.entity.Language
import com.molohala.grow.core.language.domain.entity.QLanguage.language
import com.molohala.grow.core.language.domain.entity.QMemberAndLanguage.memberAndLanguage
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class MemberLanguageQueryDslRepository(
    private val queryFactory: JPAQueryFactory
) : MemberLanguageQueryRepository {
    override fun getLanguagesByMemberId(memberId: Long): List<Language> {
        return queryFactory
            .select(
                Projections.constructor(
                    Language::class.java,
                    language.name,
                    language.id
                )
            )
            .from(memberAndLanguage)
            .where(memberAndLanguage.memberId.eq(memberId))
            .innerJoin(language)
            .on(memberAndLanguage.languageId.eq(language.id))
            .fetch()
    }
}