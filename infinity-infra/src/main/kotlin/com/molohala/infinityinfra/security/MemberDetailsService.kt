package com.molohala.infinityinfra.security

import com.molohala.infinitycore.member.application.service.MemberService
import com.molohala.infinitycore.member.domain.entity.Member
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class MemberDetailsService(
    private val memberService: MemberService
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val member: Member? = memberService.getByEmail(email)

        return MemberDetails(member)
    }
}
