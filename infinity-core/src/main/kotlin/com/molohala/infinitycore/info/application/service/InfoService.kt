package com.molohala.infinitycore.info.application.service

import com.molohala.infinitycore.info.application.dto.GithubUserInfo
import com.molohala.infinitycore.info.application.dto.res.SolvedAcInfoRes

interface InfoService {
    fun getGithubInfo(name: String): GithubUserInfo
    fun getSolvedAcInfo(name: String): SolvedAcInfoRes
}