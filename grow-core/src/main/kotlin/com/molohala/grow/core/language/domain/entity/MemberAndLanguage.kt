package com.molohala.grow.core.language.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "tbl_many_member_language")
class MemberAndLanguage(
    val memberId: Long,
    val languageId: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id",
        nullable = false,
        updatable = false
    )
    val id: Long? = null,
)