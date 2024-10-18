package com.molohala.grow.core.language.repository

import com.molohala.grow.core.language.domain.entity.Language

interface MemberLanguageQueryRepository {
    fun getLanguagesByMemberId(memberId: Long): List<Language>
}