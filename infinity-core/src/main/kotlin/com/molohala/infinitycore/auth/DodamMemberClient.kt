package com.molohala.infinitycore.auth

import com.molohala.infinitycore.auth.application.dto.DodamUserData

interface DodamMemberClient {
    suspend fun getMemberInfo(code: String): DodamUserData?
}