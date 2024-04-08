package com.molohala.infinitycore.member.domain.entity

import com.molohala.infinitycore.member.domain.consts.Social
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tbl_social_account")
class SocialAccount(
    @Id
    @Column(nullable = false, updatable = false)
    private val id : String,
    private val social: Social,
    @Column(name = "fk_member_id",nullable = false)
    private val memberId : Long
) {

}