package com.ndgndg91.springcommonbeansuse.config

import com.ndgndg91.springcommonbeans.ActiveProfileDetector
import com.ndgndg91.springcommonbeans.DistributedLockManager
import com.ndgndg91.springcommonbeans.RedisCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.redis.core.StringRedisTemplate

@Configuration
class CommonBeanConfig {

    @Bean
    fun activeProfileDetector(environment: Environment) = ActiveProfileDetector(environment)

    @Bean
    fun redisCacheManager(redisTemplate: StringRedisTemplate) = RedisCacheManager(redisTemplate)

    @Bean
    fun distributedLockManager(redisTemplate: StringRedisTemplate) = DistributedLockManager(redisTemplate)
}