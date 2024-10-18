package com.molohala.grow.core.language.repository

import com.molohala.grow.core.language.domain.entity.Language
import org.springframework.data.jpa.repository.JpaRepository

interface LanguageJpaRepository : JpaRepository<Language, Long>