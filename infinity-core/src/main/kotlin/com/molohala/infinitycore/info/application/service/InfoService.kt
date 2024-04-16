package com.molohala.infinitycore.info.application.service

import com.molohala.infinitycore.info.application.dto.GithubUserInfo

interface InfoService {
    fun getGithubInfo(name: String): GithubUserInfo
}