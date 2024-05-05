package com.molohala.grow.api.auth

import com.molohala.grow.api.response.ResponseData
import com.molohala.grow.core.auth.application.dto.req.ReissueTokenReq
import com.molohala.grow.core.auth.application.service.AuthService
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
    ) = ResponseData.ok("로그인 성공", authService.signIn(code))

    @PostMapping("/reissue")
    fun reissue(
        @RequestBody @Valid reissueTokenReq: ReissueTokenReq
    ) = ResponseData.ok("토큰 재발급 성공",authService.reissue(reissueTokenReq))

    @PostMapping("/test")
    fun test(@RequestParam("email") email: String)=
        ResponseData.ok("테스트 로그인 성공", authService.test(email))
}
