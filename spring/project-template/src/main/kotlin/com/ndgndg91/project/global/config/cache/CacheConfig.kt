package com.ndgndg91.project.global.config.cache

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Scheduler
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.cache.support.CompositeCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration
import java.util.concurrent.TimeUnit

@EnableCaching
@Configuration
class CacheConfig {
    companion object {
        const val REDIS_CACHE_MANAGER = "redisCacheManager"
        const val LOCAL_CACHE_MANAGER = "localCacheManager"

        private const val DEFAULT_EXPIRE_AFTER_WRITE = 60L
        private const val DEFAULT_MAXIMUM_SIZE = 100L
        private val DEFAULT_TIME_UNIT = TimeUnit.MINUTES
    }

    @Bean
    fun caffeineConfig(): Caffeine<Any, Any> {
        return Caffeine
            .newBuilder()
            .expireAfterWrite(DEFAULT_EXPIRE_AFTER_WRITE, DEFAULT_TIME_UNIT)
            .maximumSize(DEFAULT_MAXIMUM_SIZE)
            .scheduler(Scheduler.systemScheduler())
    }

    @Bean(LOCAL_CACHE_MANAGER)
    fun caffeineCacheManager(caffeine: Caffeine<Any, Any>): CaffeineCacheManager {
        val cacheManager = CaffeineCacheManager()
        cacheManager.setCaffeine(caffeine)

        Caches.entries.map { cache ->
            val customCache = Caffeine.newBuilder()
                .expireAfterWrite(cache.expireAfterWrite, cache.timeUnit)
                .maximumSize(cache.maximumSize)
                .scheduler(Scheduler.systemScheduler())
                .build<Any, Any>()
            cacheManager.registerCustomCache(cache.cacheName, customCache)
        }

        return cacheManager
    }

    @Bean(REDIS_CACHE_MANAGER)
    fun redisCacheManager(
        redisConnectionFactory: RedisConnectionFactory
    ): RedisCacheManager {
        val defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(DEFAULT_EXPIRE_AFTER_WRITE))
            .disableCachingNullValues()
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    StringRedisSerializer()
                )
            )

        val cacheConfigurations: Map<String, RedisCacheConfiguration> = Caches.entries.associate {
            it.cacheName to RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.of(it.expireAfterWrite, it.timeUnit.toChronoUnit()))
                .disableCachingNullValues()
                .serializeValuesWith(
                    RedisSerializationContext.SerializationPair.fromSerializer(
                        StringRedisSerializer()
                    )
                )
        }

        return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(defaultCacheConfig)
            .withInitialCacheConfigurations(cacheConfigurations)
            .build()
    }

    @Bean
    @Primary
    fun compositeCacheManager(
        caffeineCacheManager: CaffeineCacheManager,
        redisCacheManager: RedisCacheManager
    ): CacheManager {
        val compositeCacheManager = CompositeCacheManager(caffeineCacheManager, redisCacheManager)
        compositeCacheManager.setFallbackToNoOpCache(false)
        return compositeCacheManager
    }
}