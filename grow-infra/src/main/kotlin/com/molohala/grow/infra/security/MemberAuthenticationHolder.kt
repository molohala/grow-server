package com.molohala.grow.infra.security

import com.molohala.grow.core.member.application.MemberSessionHolder
import com.molohala.grow.core.member.domain.entity.Member
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class MemberAuthenticationHolder : MemberSessionHolder {
    override fun current(): Member {
        return (SecurityContextHolder.getContext().authentication.principal as MemberDetails).member!!
    }
}