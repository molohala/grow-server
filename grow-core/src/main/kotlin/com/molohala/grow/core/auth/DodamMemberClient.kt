package com.molohala.grow.core.auth

import com.molohala.grow.core.auth.application.dto.DodamUserData

interface DodamMemberClient {
    suspend fun getMemberInfo(code: String): DodamUserData?
}