package com.molohala.grow.core.notification.application.service

import com.google.firebase.messaging.FirebaseMessaging
import com.molohala.grow.core.member.application.MemberSessionHolder
import com.molohala.grow.core.notification.domain.entity.FCMToken
import com.molohala.grow.core.notification.repository.FCMTokenJpaRepository
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl(
    private val messaging: FirebaseMessaging,
    private val tokenJpaRepository: FCMTokenJpaRepository,
    private val sessionHolder: MemberSessionHolder,
) : NotificationService {
    override fun subscribe(fcmToken: String) {
        tokenJpaRepository.save(
            FCMToken(
                fcmToken = fcmToken,
                userId = sessionHolder.current().id!!
            )
        )
        messaging.subscribeToTopic(listOf(fcmToken), "/all") // for broadcasting
    }
}
