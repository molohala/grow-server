package com.molohala.infinitycore.community.domain.entity

import com.molohala.infinitycore.common.BaseIdAndTimeEntity
import jakarta.persistence.Entity

@Entity(name = "tbl_community")
class Community(
    private val content: String,
    private val memberId: Long
): BaseIdAndTimeEntity(null,null) {

}