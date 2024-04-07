package com.molohala.infinitycore.common

import jakarta.persistence.*
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseIdAndTimeEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(
        name = "id",
        nullable = false,
        updatable = false
    ) val id: Long?,
    createdAt: LocalDateTime?
) : BaseTimeEntity(createdAt) {
}