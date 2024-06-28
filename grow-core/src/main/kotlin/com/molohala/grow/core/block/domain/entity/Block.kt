package com.molohala.grow.core.block.domain.entity

import com.molohala.grow.core.common.BaseIdAndTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_block")
class Block(
    userId: Long,
    blockedUserId: Long
) : BaseIdAndTimeEntity() {
    @Column(nullable = false)
    val userId = userId

    @Column(nullable = false)
    val blockedUserId = blockedUserId
}