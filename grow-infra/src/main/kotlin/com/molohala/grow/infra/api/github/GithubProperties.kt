package com.molohala.grow.infra.api.github

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class GithubProperties(
    @Value("\${app.github.token}")
    val token: String,
)