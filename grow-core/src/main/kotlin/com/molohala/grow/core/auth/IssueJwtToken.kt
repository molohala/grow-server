package com.molohala.grow.core.auth

import com.molohala.grow.core.auth.application.dto.Token
import com.molohala.grow.core.auth.application.dto.res.ReissueTokenRes
import com.molohala.grow.core.member.domain.consts.MemberRole

interface IssueJwtToken {
    fun issueToken(email: String, role: MemberRole) : Token
    fun reissueToken(refreshToken : String) : ReissueTokenRes
}