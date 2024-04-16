package com.molohala.infinitycore.info.application.service

import com.molohala.infinitycommon.exception.custom.CustomException
import com.molohala.infinitycore.info.GithubInfoClient
import com.molohala.infinitycore.info.application.dto.GithubUserInfo
import com.molohala.infinitycore.info.exception.InfoExceptionCode
import org.springframework.stereotype.Service

@Service
class InfoServiceImpl(
    private val githubInfoClient: GithubInfoClient
) : InfoService {
    override fun getGithubInfo(name: String): GithubUserInfo {
        return githubInfoClient.getInfo(name) ?: throw CustomException(InfoExceptionCode.USER_NOT_FOUND)
    }
}