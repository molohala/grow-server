package com.molohala.grow.core.like.domain.entity

import com.molohala.grow.core.common.BaseIdAndTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity(name = "tbl_like")
class Like(
    communityId: Long,
    memberId: Long,
    id: Long? = null,
    createdAt: LocalDateTime? = null,
) : BaseIdAndTimeEntity(id, createdAt) {
    @Column(nullable = false)
    var communityId = communityId
        private set

    @Column(nullable = false)
    var memberId = memberId
        private set
}
