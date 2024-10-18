package com.molohala.grow.core.notification.application.dto.req

import com.fasterxml.jackson.annotation.JsonCreator

data class FCMTokenAddRequest @JsonCreator constructor(val fcmToken: String)
