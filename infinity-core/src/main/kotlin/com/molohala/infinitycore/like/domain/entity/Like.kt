package com.molohala.infinitycore.like.domain.entity;

import com.molohala.infinitycore.common.BaseIdAndTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity(name = "tbl_like")
class Like(
    communityId: Long,
    memberId: Long
): BaseIdAndTimeEntity(null,null) {

    @Column(nullable = false)
    var communityId = communityId
        private set

    @Column(nullable = false)
    var memberId = memberId
        private set
}
