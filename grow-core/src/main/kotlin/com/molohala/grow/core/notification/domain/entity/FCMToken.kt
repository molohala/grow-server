package com.molohala.grow.core.notification.domain.entity

import com.molohala.grow.core.common.BaseIdAndTimeEntity
import jakarta.persistence.Entity

@Entity
data class FCMToken(
    val fcmToken: String,
    val userId: Long
) : BaseIdAndTimeEntity()
