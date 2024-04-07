package com.molohala.infinitycore.auth.application.dto

data class Token(
    val accessToken: String,
    val refreshToken: String,
)
