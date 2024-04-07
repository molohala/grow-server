package com.molohala.infinitycore.member.repository

import com.molohala.infinitycore.member.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberJpaRepository: JpaRepository<Member, Long> {
    fun findByEmail(email: String) : Member?
}