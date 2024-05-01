package com.molohala.infinitycore.rank.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.molohala.infinitycore.rank.domain.dto.RedisSocialAccount
import com.molohala.infinitycore.utils.RedisUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer

@Configuration
@EnableRedisRepositories
class RankRedisConfig(
    private val objectMapper: ObjectMapper,
) {
    @Bean
    fun rankRedisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, RedisSocialAccount> {
        return RedisUtils.createTemplate(
            connectionFactory,
            Jackson2JsonRedisSerializer(objectMapper, RedisSocialAccount::class.java)
        )
    }
}