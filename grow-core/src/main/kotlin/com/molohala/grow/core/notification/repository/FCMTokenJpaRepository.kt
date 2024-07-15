package com.molohala.grow.core.notification.repository

import com.molohala.grow.core.notification.domain.entity.FCMToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FCMTokenJpaRepository : JpaRepository<FCMToken, Long> {
    fun getFCMTokenByUserIdIs(userId: Long): FCMToken?
}
