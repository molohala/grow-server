package com.molohala.infinitycore.member.application

import com.molohala.infinitycore.member.domain.entity.Member

interface MemberSessionHolder {
    fun current(): Member
}