package com.molohala.grow.core.language.application.dto.req

import com.fasterxml.jackson.annotation.JsonCreator

data class EditLanguageReq @JsonCreator constructor(
    val langs: List<Long>
)