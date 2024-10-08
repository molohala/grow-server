package com.molohala.infinitycore.info.application.dto

data class GithubUserInfo(
    val name: String,
    val avatarUrl: String,
    val bio: String,

    val totalCommits: Long,
    val weekCommits: List<GithubContribute>,
    val todayCommits: GithubContribute,
)