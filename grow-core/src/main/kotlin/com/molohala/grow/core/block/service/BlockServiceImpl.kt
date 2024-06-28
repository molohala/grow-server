package com.molohala.grow.core.block.service

import com.molohala.grow.core.block.domain.entity.Block
import com.molohala.grow.core.block.domain.repository.BlockRepository
import com.molohala.grow.core.member.application.MemberSessionHolder
import com.molohala.grow.core.member.domain.exception.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BlockServiceImpl(
    private val blockRepository: BlockRepository,
    private val memberSessionHolder: MemberSessionHolder
) : BlockService {

    @Transactional(rollbackFor = [Exception::class])
    override fun blockUser(blockedUserId: Long) {
        val member = memberSessionHolder.current()
        val exists = blockRepository.existsByUserIdAndBlockedUserId(userId = member.id!!, blockedUserId = blockedUserId)
        if (exists) {
            throw AccessDeniedException()
        }
        val blockEntity = Block(
            userId = member.id,
            blockedUserId = blockedUserId,
        )
        blockRepository.save(blockEntity)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun allowUser(blockedUserId: Long) {
        val member = memberSessionHolder.current()
        val exists = blockRepository.existsByUserIdAndBlockedUserId(userId = member.id!!, blockedUserId = blockedUserId)
        if (!exists) {
            throw AccessDeniedException()
        }
        blockRepository.deleteByUserIdAndBlockedUserId(
            blockedUserId = blockedUserId,
            userId = member.id
        )
    }
}