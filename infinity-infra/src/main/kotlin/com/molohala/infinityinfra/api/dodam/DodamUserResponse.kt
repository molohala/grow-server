package com.molohala.infinityinfra.api.dodam

import com.molohala.infinitycore.auth.application.dto.DodamUserData

data class DodamUserResponse(
    val state: Long,
    val message: String,
    val data: DodamUserData
)