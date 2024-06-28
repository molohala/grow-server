package com.molohala.grow.core.block.service

interface BlockService {
    fun blockUser(blockedUserId: Long)
    fun allowUser(blockedUserId: Long)
}