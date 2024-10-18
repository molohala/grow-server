package com.molohala.grow.core.member.domain.consts

enum class MemberJob(val display: String) {
    SERVER("Server"),
    WEB("Web"),
    ANDROID("Android"),
    IOS("iOS"),
    DESIGNER("Designer"),
    GAME("Game"),
    DEVELOPER("");

    companion object {
        fun parse(job: String?): MemberJob? {
            if (job != null) return entries.find { it.display.lowercase() == job.lowercase() || it.name.lowercase() == job.lowercase() }
            return null
        }
    }
}