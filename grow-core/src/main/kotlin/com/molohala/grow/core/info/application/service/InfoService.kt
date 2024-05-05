package com.molohala.grow.core.info.application.service

import com.molohala.grow.core.info.application.dto.GithubUserInfo
import com.molohala.grow.core.info.application.dto.res.InfoRes
import com.molohala.grow.core.info.application.dto.res.SolvedAcInfoRes

interface InfoService {
    fun getGithubInfo(name: String): GithubUserInfo
    fun getSolvedAcInfo(name: String): SolvedAcInfoRes
    fun getMyInfo(): InfoRes
    fun getUserInfo(userId: Long): InfoRes
    fun submitGithubId(name: String)
    fun submitSolvedAcInfo(name: String)
}