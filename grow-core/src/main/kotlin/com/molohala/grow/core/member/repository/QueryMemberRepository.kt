package com.molohala.grow.core.member.repository

import com.molohala.grow.core.member.domain.entity.Member

interface QueryMemberRepository {
    fun findAll(userId: Long): List<Member>
}