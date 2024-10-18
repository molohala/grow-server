package com.molohala

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*

@EnableScheduling
@SpringBootApplication
class GrowApplication {
    @PostConstruct
    fun init() {
        // Timezone Fix
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    }
}

fun main(args: Array<String>) {
    runApplication<GrowApplication>(*args)
}
