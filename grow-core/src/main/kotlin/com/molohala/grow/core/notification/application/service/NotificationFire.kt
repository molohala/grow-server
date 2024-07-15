package com.molohala.grow.core.notification.application.service

interface NotificationFire {
    fun sendTo(title: String, body: String, userId: Long)
}
