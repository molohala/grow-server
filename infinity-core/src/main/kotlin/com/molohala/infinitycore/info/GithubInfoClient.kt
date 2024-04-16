package com.molohala.infinitycore.info

import com.molohala.infinitycore.info.application.dto.GithubUserInfo

interface GithubInfoClient {
    fun getInfo(name: String): GithubUserInfo?
}