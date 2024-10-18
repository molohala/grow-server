package com.molohala.grow.core.language.application.service

import com.molohala.grow.core.language.domain.entity.Language

interface LanguageService {
    fun updateUsingLanguages(langs: List<Long>)
    fun getUsingLanguagesByMe(): List<Language>
    fun getUsingLanguagesByOther(user: Long): List<Language>
    fun getAvailableLanguage(): List<Language>
}