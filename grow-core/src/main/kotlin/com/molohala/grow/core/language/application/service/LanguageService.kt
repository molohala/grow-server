package com.molohala.grow.core.language.application.service

import com.molohala.grow.core.language.domain.entity.Language

interface LanguageService {
    fun updateUsingLanguages(langs: List<Long>)
    fun getUsingLanguages(): List<Language>
    fun getAvailableLanguage(): List<Language>
}