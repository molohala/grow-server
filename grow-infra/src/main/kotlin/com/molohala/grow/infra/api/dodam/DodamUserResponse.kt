package com.molohala.grow.infra.api.dodam

import com.molohala.grow.core.auth.application.dto.DodamUserData

data class DodamUserResponse(
    val state: Long,
    val message: String,
    val data: DodamUserData
)