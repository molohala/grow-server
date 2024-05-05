package com.molohala.grow.core.member.application.service

import com.molohala.grow.core.member.repository.MemberJpaRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberJpaRepository: MemberJpaRepository
) {
    fun getByEmail(email: String) = memberJpaRepository.findByEmail(email)
}