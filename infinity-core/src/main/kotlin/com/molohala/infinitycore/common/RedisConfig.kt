package com.molohala.infinitycore.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableRedisRepositories
class RedisConfig {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory = JedisConnectionFactory()

    @Bean
    fun redisTemplate(): RedisTemplate<String, String> {
        return StringRedisTemplate().apply { connectionFactory = redisConnectionFactory() }
    }
}