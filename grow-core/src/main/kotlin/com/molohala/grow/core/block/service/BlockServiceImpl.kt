package com.molohala.grow.core.block.service

import com.molohala.grow.core.block.application.dto.res.BlockRes
import com.molohala.grow.core.block.domain.entity.Block
import com.molohala.grow.core.block.repository.BlockRepository
import com.molohala.grow.core.member.application.MemberSessionHolder
import com.molohala.grow.core.member.domain.entity.Member
import com.molohala.grow.core.member.domain.exception.AccessDeniedException
import com.molohala.grow.core.member.repository.QueryMemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BlockServiceImpl(
    private val blockRepository: BlockRepository,
    private val queryMemberRepository: QueryMemberRepository,
    private val memberSessionHolder: MemberSessionHolder
) : BlockService {

    @Transactional(rollbackFor = [Exception::class])
    override fun blockUser(blockedUserId: Long) {
        val member = memberSessionHolder.current()
        val exists = blockRepository.existsByUserIdAndBlockedUserId(userId = member.id!!, blockedUserId = blockedUserId)
        if (exists || member.id == blockedUserId) {
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
        if (!exists || member.id == blockedUserId) {
            throw AccessDeniedException()
        }
        blockRepository.deleteByUserIdAndBlockedUserId(
            blockedUserId = blockedUserId,
            userId = member.id
        )
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun getAll(): List<Member> {
        val member = memberSessionHolder.current()
        val blocks = queryMemberRepository.findAll(userId = member.id!!)
        return blocks
    }
}