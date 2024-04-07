package com.molohala.infinitycore.auth

import com.molohala.infinitycore.auth.application.dto.Token
import com.molohala.infinitycore.auth.application.dto.res.ReissueTokenRes
import com.molohala.infinitycore.member.domain.consts.MemberRole

interface IssueJwtToken {
    fun issueToken(email: String, role: MemberRole) : Token
    fun reissueToken(refreshToken : String) : ReissueTokenRes
}