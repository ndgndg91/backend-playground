package com.ndgndg91.project.global.config.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    companion object {
        const val REDIS_PROPERTY: String = "infra.redis"
    }

    @ConfigurationProperties(prefix = REDIS_PROPERTY)
    data class RedisProperty(
        val host: String,
        val port: Int = 6379,
        val password: String? = null,
        val database: Int,
    )

    @Bean
    fun redisConnectionFactory(redisProperty: RedisProperty): RedisConnectionFactory {
        val redisConfig = RedisStandaloneConfiguration(redisProperty.host, redisProperty.port)
        redisConfig.database = redisProperty.database
        redisProperty.password?.let { redisConfig.setPassword(it) }
        return LettuceConnectionFactory(redisConfig)
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = redisConnectionFactory
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        template.setEnableTransactionSupport(true)
        template.afterPropertiesSet()
        return template
    }
}