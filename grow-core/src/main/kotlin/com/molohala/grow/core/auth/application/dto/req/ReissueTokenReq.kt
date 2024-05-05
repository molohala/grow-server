package com.molohala.grow.core.auth.application.dto.req

import com.fasterxml.jackson.annotation.JsonCreator

data class ReissueTokenReq @JsonCreator constructor(
    val refreshToken: String
)