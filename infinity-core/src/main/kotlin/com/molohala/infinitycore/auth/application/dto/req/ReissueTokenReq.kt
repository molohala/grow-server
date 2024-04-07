package com.molohala.infinitycore.auth.application.dto.req

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

data class ReissueTokenReq @JsonCreator constructor(
    val refreshToken: String
)