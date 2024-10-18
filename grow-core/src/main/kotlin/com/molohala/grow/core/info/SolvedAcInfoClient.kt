package com.molohala.grow.core.info

import com.molohala.grow.core.info.application.dto.SolvedAcSolves
import com.molohala.grow.core.info.application.dto.SolvedAcUserInfo

interface SolvedAcInfoClient {
    fun getUserInfo(name: String): SolvedAcUserInfo?
    fun getUserGrass(name: String): List<SolvedAcSolves>
}