package com.molohala.grow.core.common

import jakarta.persistence.*
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseIdAndTimeEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(
        name = "id",
        nullable = false,
        updatable = false
    ) val id: Long? = null,
    createdAt: LocalDateTime? = null
) : BaseTimeEntity(createdAt)