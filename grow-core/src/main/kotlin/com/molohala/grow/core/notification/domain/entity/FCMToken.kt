package com.molohala.grow.core.notification.domain.entity

import com.molohala.grow.core.common.BaseIdAndTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity(name = "tbl_fcm_tokens")
data class FCMToken(
    @Column(nullable = false)
    val fcmToken: String,

    @Column(nullable = false)
    val userId: Long
) : BaseIdAndTimeEntity()
