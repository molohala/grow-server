package com.molohala.grow.core.language.application.runner

import com.molohala.grow.core.language.domain.entity.Language
import com.molohala.grow.core.language.repository.LanguageJpaRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class LanguageLoader(
    private val languageJpaRepository: LanguageJpaRepository
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val languages = languageJpaRepository.findAll()
        val m = listOf(
            "Python",
            "HTML",
            "CSS",
            "JavaScript",
            "TypeScript",
            "C",
            "C++",
            "C#",
            "Kotlin",
            "Java",
            "Swift",
            "Dart",
            "Go",
            "Ruby",
            "Rust",
            "SQL",
            "PHP",
            "Scala",
            "Lua",
        )
        val addLang = ArrayList<Language>()

        for (s in m) if (languages.none { it.name.lowercase() == s.lowercase() }) addLang.add(Language(s))

        languageJpaRepository.saveAll(addLang)
    }

}