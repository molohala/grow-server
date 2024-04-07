package com.molohala.infinityapi.auth

import com.molohala.infinitycore.auth.application.dto.req.ReissueTokenReq
import com.molohala.infinitycore.auth.application.service.AuthService
import com.molohala.infinitycore.auth.application.dto.res.ReissueTokenRes
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/sign-in")
    suspend fun signIn(
        @RequestParam("code") @Valid code: String
    ) = authService.signIn(code)

    @PostMapping("/reissue")
    fun reissue(
        @RequestBody @Valid reissueTokenReq: ReissueTokenReq
    ): ReissueTokenRes {
        return authService.reissue(reissueTokenReq)
    }
}
