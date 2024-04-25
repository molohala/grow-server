package com.molohala.infinitycore.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.molohala.infinitycore.member.domain.entity.RedisSocialAccount
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
@EnableRedisRepositories
class RedisConfig(
    private val objectMapper: ObjectMapper,
) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory = JedisConnectionFactory()

    @Bean
    fun redisTemplate(): RedisTemplate<String, RedisSocialAccount> {
        return createTemplate(redisConnectionFactory(), Jackson2JsonRedisSerializer(objectMapper, RedisSocialAccount::class.java))
//        return StringRedisTemplate().apply { connectionFactory = redisConnectionFactory() }
    }

    private fun <K, V, S : RedisSerializer<*>> createTemplate(factory: RedisConnectionFactory, valueSerializer: S): RedisTemplate<K, V> {
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