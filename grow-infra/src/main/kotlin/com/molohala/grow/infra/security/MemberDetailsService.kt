package com.molohala.grow.infra.security

import com.molohala.grow.common.exception.GlobalExceptionCode
import com.molohala.grow.common.exception.custom.CustomException
import com.molohala.grow.core.member.application.service.MemberService
import com.molohala.grow.core.member.domain.consts.MemberState
import com.molohala.grow.core.member.domain.entity.Member
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class MemberDetailsService(
    private val memberService: MemberService
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val member: Member? = memberService.getByEmail(email)
        if (member?.state == MemberState.DELETED) throw CustomException(GlobalExceptionCode.USER_IS_DELETED)
        return MemberDetails(member)
    }
}
