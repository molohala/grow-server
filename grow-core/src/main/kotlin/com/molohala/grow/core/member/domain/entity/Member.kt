package com.molohala.grow.core.member.domain.entity

import com.molohala.grow.core.common.BaseIdAndTimeEntity
import com.molohala.grow.core.member.domain.consts.MemberJob
import com.molohala.grow.core.member.domain.consts.MemberRole
import com.molohala.grow.core.member.domain.consts.MemberState
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Suppress("unused")
@Entity(name = "tbl_member")
class Member(
    name: String,
    email: String,
    role: MemberRole,
    state: MemberState,
    job: MemberJob = MemberJob.DEVELOPER,
    bio: String = "",
) : BaseIdAndTimeEntity(null, null) {
    @Column(nullable = false)
    var name: String = name
        private set

    @Column(nullable = false, unique = true)
    var email: String = email
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: MemberRole = role
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var state: MemberState = state
        private set

    @Column(nullable = false)
    var bio: String = bio
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var job: MemberJob = job
        private set

    fun updateEmail(email: String) {
        this.email = email
    }

    fun updateInfo(bio: String?, job: MemberJob?) {
        if (bio != null) this.bio = bio
        if (job != null) this.job = job
    }

    fun markDelete(): Member {
        state = MemberState.DELETED
        return this
    }
}