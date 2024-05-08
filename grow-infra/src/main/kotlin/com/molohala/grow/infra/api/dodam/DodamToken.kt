package com.molohala.grow.infra.api.dodam

data class DodamToken(
    val access_token: String,
    val refresh_token: String,
    val token_type: String,
    val expires_in: String
)
