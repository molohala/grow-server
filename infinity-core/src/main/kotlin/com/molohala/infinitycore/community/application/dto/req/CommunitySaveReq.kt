package com.molohala.infinitycore.community.application.dto.req

import com.fasterxml.jackson.annotation.JsonCreator

data class CommunitySaveReq @JsonCreator constructor(
    val content: String
) {
}