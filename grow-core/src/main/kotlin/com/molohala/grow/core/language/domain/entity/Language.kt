package com.molohala.grow.core.language.domain.entity

import jakarta.persistence.*

@Entity(name = "tbl_langs")
data class Language(
    val name: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "id")
    val id: Long? = null
)