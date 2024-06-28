package com.molohala.grow.core.block.repository

import com.molohala.grow.core.block.domain.entity.Block
import org.springframework.data.jpa.repository.JpaRepository

interface BlockRepository : JpaRepository<Block, Long> {
    fun existsByUserIdAndBlockedUserId(userId: Long, blockedUserId: Long): Boolean
    fun deleteByUserIdAndBlockedUserId(userId: Long, blockedUserId: Long)
    fun findByUserId(userId: Long): List<Block>
}