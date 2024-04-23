package com.molohala.infinitycore.info.application.dto.req

import com.fasterxml.jackson.annotation.JsonCreator

data class NewSocialAccountReq @JsonCreator constructor(
    val socialId: String
)