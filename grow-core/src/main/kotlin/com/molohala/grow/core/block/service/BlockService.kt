package com.molohala.grow.core.block.service

import com.molohala.grow.core.member.domain.entity.Member

interface BlockService {
    fun blockUser(blockedUserId: Long)
    fun allowUser(blockedUserId: Long)
    fun getAll(): List<Member>
}