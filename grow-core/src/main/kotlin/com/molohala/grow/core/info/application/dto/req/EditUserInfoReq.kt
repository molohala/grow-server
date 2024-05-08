package com.molohala.grow.core.info.application.dto.req

import com.fasterxml.jackson.annotation.JsonCreator

data class EditUserInfoReq @JsonCreator constructor(
    val bio: String
)