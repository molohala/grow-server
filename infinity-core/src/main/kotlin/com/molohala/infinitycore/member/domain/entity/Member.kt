package com.molohala.infinitycore.member.domain.entity

import com.molohala.infinitycore.common.BaseIdAndTimeEntity
import com.molohala.infinitycore.member.domain.consts.MemberRole
import com.molohala.infinitycore.member.domain.consts.MemberState
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity(name = "tbl_member")
class Member(
    name: String,
    email: String,
    role: MemberRole,
    state: MemberState,
):BaseIdAndTimeEntity(null,null) {
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

    fun update(email: String){
        this.email = email
    }
}