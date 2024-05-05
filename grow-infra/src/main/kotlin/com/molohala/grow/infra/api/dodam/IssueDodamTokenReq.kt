package com.molohala.grow.infra.api.dodam

data class IssueDodamTokenReq(
    val code: String,
    val client_id: String,
    val client_secret: String
)