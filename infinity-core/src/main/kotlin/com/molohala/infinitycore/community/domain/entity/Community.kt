package com.molohala.infinitycore.community.domain.entity

import com.molohala.infinitycore.common.BaseIdAndTimeEntity
import com.molohala.infinitycore.community.domain.consts.CommunityState
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity(name = "tbl_community")
class Community(
    content: String,
    state: CommunityState,
    memberId: Long
): BaseIdAndTimeEntity(null,null) {

    @Column(nullable = false)
    var content = content
        private set

    @Column(nullable = false)
    var state = state
        private set

    @Column(nullable = false)
    var memberId = memberId
        private set

    fun delete(){
        this.state=CommunityState.DELETED
    }

    fun modify(content: String){
        this.content=content
    }
}