package com.molohala.grow.core.like.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class LikeRedisConfig {
    @Bean
    fun likeRedisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Long> {
        val redisTemplate = RedisTemplate<String, Long>()
        val st = GenericToStringSerializer(Long::class.java)
        redisTemplate.connectionFactory = connectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = st
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = st
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }
}