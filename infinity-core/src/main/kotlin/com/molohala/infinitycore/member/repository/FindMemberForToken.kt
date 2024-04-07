package com.molohala.infinitycore.member.repository

import org.springframework.stereotype.Component

@Component
class FindMemberForToken(
    private val memberJpaRepository: MemberJpaRepository
){
    fun findByEmail(email: String) = memberJpaRepository.findByEmail(email)
}