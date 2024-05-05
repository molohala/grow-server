package com.molohala.grow.core.info.application.dto.res

import java.time.LocalDateTime

data class InfoRes(
    val id: Long,
    val email: String,
    val name: String,
    val createdAt: LocalDateTime,
    val socialAccounts: List<SocialAccountRes>,
)