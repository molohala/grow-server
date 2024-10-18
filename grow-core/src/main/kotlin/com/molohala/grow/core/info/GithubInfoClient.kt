package com.molohala.grow.core.info

import com.molohala.grow.core.info.application.dto.GithubUserInfo

interface GithubInfoClient {
    fun getInfo(name: String): GithubUserInfo?
}