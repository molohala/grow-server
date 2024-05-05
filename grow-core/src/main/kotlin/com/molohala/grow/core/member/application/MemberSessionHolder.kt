package com.molohala.grow.core.member.application

import com.molohala.grow.core.member.domain.entity.Member

interface MemberSessionHolder {
    fun current(): Member
}