package com.molohala.grow.core.language.repository

import com.molohala.grow.core.language.domain.entity.MemberAndLanguage
import org.springframework.data.jpa.repository.JpaRepository

interface MemberLanguageJpaRepository : JpaRepository<MemberAndLanguage, Long> {
    fun findAllByMemberIdIs(memberId: Long): List<MemberAndLanguage>
}