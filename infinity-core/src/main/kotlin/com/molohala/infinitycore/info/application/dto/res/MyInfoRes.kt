package com.molohala.infinitycore.info.application.dto.res

import java.time.LocalDateTime

data class MyInfoRes(
    val id: Long,
    val email: String,
    val name: String,
    val createdAt: LocalDateTime,
    val socialAccounts: List<SocialAccountRes>,
)