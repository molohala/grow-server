package com.molohala.infinityinfra.security

import com.molohala.infinitycore.member.domain.entity.Member
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

class MemberAuthenticationHolder {
    @Component
    internal class MemberAuthenticationHolder {
        fun current(): Member? {
            return (SecurityContextHolder.getContext().authentication.principal as MemberDetails).member
        }
    }
}