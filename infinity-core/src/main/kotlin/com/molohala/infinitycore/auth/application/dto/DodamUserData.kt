package com.molohala.infinitycore.auth.application.dto

data class DodamUserData(
    val uniqueId: String,
    val grade: Long,
    val room: Long,
    val number: Long,
    val name: String,
    val profileImage: String,
    val role: String,
    val email: String
)