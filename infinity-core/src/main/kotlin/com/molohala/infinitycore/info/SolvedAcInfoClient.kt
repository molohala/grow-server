package com.molohala.infinitycore.info

import com.molohala.infinitycore.info.application.dto.SolvedAcSolves
import com.molohala.infinitycore.info.application.dto.SolvedAcUserInfo

interface SolvedAcInfoClient {
    fun getUserInfo(name: String): SolvedAcUserInfo?
    fun getUserGrass(name: String): List<SolvedAcSolves>
}