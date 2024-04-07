package com.molohala.infinityinfra.token

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtProperties(
    @Value("\${app.jwt.secret}")
    val secret: String,
    @Value("\${app.jwt.accessExpire}")
    val accessExpire: Long,
    @Value("\${app.jwt.refreshExpire}")
    val refreshExpire: Long,
    @Value("\${app.jwt.issuer}")
    val issuer: String,
)