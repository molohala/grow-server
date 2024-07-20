package com.molohala.grow.api.notification.presentation

import com.molohala.grow.api.response.Response
import com.molohala.grow.core.notification.application.dto.req.FCMTokenAddRequest
import com.molohala.grow.core.notification.application.service.NotificationService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/notification")
@RestController
class NotificationController(
    private val notificationService: NotificationService
) {
    @PostMapping
    fun subscribe(@RequestBody @Valid request: FCMTokenAddRequest): Response {
        notificationService.subscribe(request.fcmToken) // ok
        return Response.ok("토큰 저장 성공")
    }
}
