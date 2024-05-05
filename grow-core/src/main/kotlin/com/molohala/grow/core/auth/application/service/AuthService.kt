package com.molohala.grow.core.auth.application.service

import com.molohala.grow.core.auth.application.dto.Token
import com.molohala.grow.core.auth.application.dto.req.ReissueTokenReq
import com.molohala.grow.core.auth.application.dto.res.ReissueTokenRes

interface AuthService {
    suspend fun signIn(code : String): Token
    fun reissue(reissueTokenReq: ReissueTokenReq): ReissueTokenRes
    fun test(email: String): Token
}