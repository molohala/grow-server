package com.molohala.infinitycore.auth.application.service

import com.molohala.infinitycore.auth.application.dto.Token
import com.molohala.infinitycore.auth.application.dto.req.ReissueTokenReq
import com.molohala.infinitycore.auth.application.dto.res.ReissueTokenRes

interface AuthService {
    suspend fun signIn(code : String): Token
    fun reissue(reissueTokenReq: ReissueTokenReq): ReissueTokenRes
    fun test(email: String):Token
}