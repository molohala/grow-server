package com.molohala.infinitycore.common

import jakarta.persistence.*
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseTimeEntity(
    createdAt : LocalDateTime?
) {
    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(6)")
    val createdAt: LocalDateTime = createdAt ?: LocalDateTime.now()
}