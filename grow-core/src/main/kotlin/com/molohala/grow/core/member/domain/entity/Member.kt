package com.molohala.grow.core.member.domain.entity

import com.molohala.grow.core.common.BaseIdAndTimeEntity
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
    bio: String = "",
) : BaseIdAndTimeEntity(null, null) {
    @Column(nullable = false)
    var name = name
        private set

    @Column(nullable = false, unique = true)
    var email = email
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role = role
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var state = state
        private set

    @Column(nullable = true)
    var bio = bio
        private set

    fun updateEmail(email: String) {
        this.email = email
    }

    fun updateBio(bio: String) {
        this.bio = bio
    }
}