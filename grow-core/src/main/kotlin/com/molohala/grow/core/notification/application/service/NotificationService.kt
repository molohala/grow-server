package com.molohala.grow.core.notification.application.service

interface NotificationService {
    fun subscribe(fcmToken: String)
}
