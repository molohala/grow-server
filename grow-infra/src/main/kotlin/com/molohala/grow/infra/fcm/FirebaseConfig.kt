package com.molohala.grow.infra.fcm

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class FirebaseConfig {
    private val log = LoggerFactory.getLogger(FirebaseConfig::class.java)
    @PostConstruct
    fun init() {
        try {
            val stream = ClassPathResource("grow-firebase.json").inputStream
            val opt = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(stream))
                .build()
            FirebaseApp.initializeApp(opt)
        } catch (e: Exception) {
            log.error("Error while initializing firebase: ", e)
        }
    }

    @Bean
    fun firebaseMessaging(): FirebaseMessaging = FirebaseMessaging.getInstance()
}
