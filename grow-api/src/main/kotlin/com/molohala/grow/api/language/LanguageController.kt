package com.molohala.grow.api.language

import com.molohala.grow.api.response.Response
import com.molohala.grow.api.response.ResponseData
import com.molohala.grow.core.language.application.dto.req.EditLanguageReq
import com.molohala.grow.core.language.application.service.LanguageService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/language")
class LanguageController(
    private val languageService: LanguageService
) {
    @GetMapping
    fun getAll() = ResponseData.ok("언어 리스트 조회 완료", languageService.getAvailableLanguage())

    @GetMapping("/me")
    fun languages() = ResponseData.ok("사용하는 언어 조회 완료", languageService.getUsingLanguages())

    @PatchMapping("/me")
    fun editLanguage(@RequestBody data: EditLanguageReq): Response {
        languageService.updateUsingLanguages(data.langs)
        return Response.ok("사용하는 언어 갱신 완료")
    }
}