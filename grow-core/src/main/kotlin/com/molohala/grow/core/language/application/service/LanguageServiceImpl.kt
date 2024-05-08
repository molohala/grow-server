package com.molohala.grow.core.language.application.service

import com.molohala.grow.common.exception.custom.CustomException
import com.molohala.grow.core.language.domain.entity.Language
import com.molohala.grow.core.language.domain.entity.MemberAndLanguage
import com.molohala.grow.core.language.exception.LanguageExceptionCode
import com.molohala.grow.core.language.repository.LanguageJpaRepository
import com.molohala.grow.core.language.repository.MemberLanguageJpaRepository
import com.molohala.grow.core.language.repository.MemberLanguageQueryRepository
import com.molohala.grow.core.member.application.MemberSessionHolder
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class LanguageServiceImpl(
    private val memberSessionHolder: MemberSessionHolder,
    private val languageJpaRepository: LanguageJpaRepository,
    private val memberLanguageJpaRepository: MemberLanguageJpaRepository,
    private val memberLanguageQueryRepository: MemberLanguageQueryRepository
) : LanguageService {
    @Transactional(rollbackOn = [Exception::class])
    override fun updateUsingLanguages(langs: List<Long>) {
        val member = memberSessionHolder.current()
        val dbLang = languageJpaRepository.findAll()

        val owning =
            memberLanguageJpaRepository
                .findAllByMemberIdIs(member.id!!)

        memberLanguageJpaRepository.deleteAllInBatch(owning.filter { it.languageId !in langs })

        val toAdd = ArrayList<Language>()

        for (lang in langs) {
            val ent = dbLang.find { it.id == lang }
            if (ent == null)
                throw CustomException(LanguageExceptionCode.LANGUAGE_NOT_FOUND)
            if (owning.none { it.languageId == lang }) toAdd.add(ent) // not owning
        }

        memberLanguageJpaRepository.saveAllAndFlush(
            toAdd.map { MemberAndLanguage(member.id, it.id!!) }
        )
    }

    override fun getUsingLanguages(): List<Language> {
        val member = memberSessionHolder.current()
        return memberLanguageQueryRepository.getLanguagesByMemberId(member.id!!)
    }

    override fun getAvailableLanguage(): List<Language> = languageJpaRepository.findAll()
}