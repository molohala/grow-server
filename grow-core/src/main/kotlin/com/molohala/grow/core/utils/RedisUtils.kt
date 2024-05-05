package com.molohala.grow.core.utils

import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

object RedisUtils {
    fun <K, V, S : RedisSerializer<*>> createTemplate(factory: RedisConnectionFactory, valueSerializer: S): RedisTemplate<K, V> {
        val redisTemplate = RedisTemplate<K, V>()
        redisTemplate.connectionFactory = factory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = valueSerializer
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = GenericJackson2JsonRedisSerializer()
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }
}