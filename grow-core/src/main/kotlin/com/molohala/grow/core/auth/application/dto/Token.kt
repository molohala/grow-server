package com.molohala.grow.core.auth.application.dto

data class Token(
    val accessToken: String,
    val refreshToken: String,
)
