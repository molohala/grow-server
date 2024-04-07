package com.molohala.infinitycore.member.application.service

import com.molohala.infinitycore.member.repository.MemberJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberJpaRepository: MemberJpaRepository
) {
    fun getByEmail(email: String) = memberJpaRepository.findByEmail(email)
    fun getById(id: Long) = memberJpaRepository.findByIdOrNull(id)
}