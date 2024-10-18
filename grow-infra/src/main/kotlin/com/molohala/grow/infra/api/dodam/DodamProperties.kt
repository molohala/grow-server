package com.molohala.grow.infra.api.dodam

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class DodamProperties(
    @Value("\${app.dodam.clientId}")
    val clientId: String,

    @Value("\${app.dodam.clientSecret}")
    val clientSecret: String,

    @Value("\${app.dodam.tokenUrl}")
    val tokenUrl: String,

    @Value("\${app.dodam.infoUrl}")
    val infoUrl: String
)