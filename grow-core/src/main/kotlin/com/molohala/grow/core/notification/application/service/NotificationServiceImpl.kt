package com.molohala.grow.core.notification.application.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.molohala.grow.core.member.application.MemberSessionHolder
import com.molohala.grow.core.notification.domain.entity.FCMToken
import com.molohala.grow.core.notification.repository.FCMTokenJpaRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationServiceImpl(
    private val messaging: FirebaseMessaging,
    private val tokenJpaRepository: FCMTokenJpaRepository,
    private val sessionHolder: MemberSessionHolder,
) : NotificationService, NotificationFire {
    private val log = LoggerFactory.getLogger(NotificationService::class.java)

    @Transactional(rollbackFor = [Exception::class])
    override fun subscribe(fcmToken: String) {
        tokenJpaRepository.save(
            FCMToken(
                fcmToken = fcmToken,
                userId = sessionHolder.current().id!!
            )
        )
        messaging.subscribeToTopic(listOf(fcmToken), "/all") // for broadcasting
    }

    override fun sendTo(title: String, body: String, userId: Long) {
        val fcm = tokenJpaRepository.getFCMTokenByUserIdIs(userId)
            ?: return

        val msg = Message.builder()
            .setToken(fcm.fcmToken)
            .setNotification(
                Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build()
            )
            .build()

        try {
            messaging.send(msg)
        } catch (e: FirebaseMessagingException) {
            log.error("Firebase error: ", e)
        }
    }
}
