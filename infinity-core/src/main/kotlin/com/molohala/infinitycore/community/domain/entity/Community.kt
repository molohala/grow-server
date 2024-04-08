package com.molohala.infinitycore.community.domain.entity

import com.molohala.infinitycore.common.BaseIdAndTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity(name = "tbl_community")
class Community(
    content: String,
    memberId: Long
): BaseIdAndTimeEntity(null,null) {

    @Column(nullable = false)
    var content = content
        private set

    @Column(nullable = false)
    var memberId = memberId
        private set

    fun modify(content: String){
        this.content=content
    }
}