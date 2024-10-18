package com.molohala.grow.core.auth.domain.consts

enum class JwtType(val jwtType: String) {
    ACCESS_TOKEN("ACCESS"),
    REFRESH_TOKEN("REFRESH")
}