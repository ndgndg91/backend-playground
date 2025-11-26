package com.ndgndg91.project.dummy.service

import com.ndgndg91.project.global.config.cache.CacheConfig.Companion.LOCAL_CACHE_MANAGER
import com.ndgndg91.project.global.config.cache.CacheConfig.Companion.REDIS_CACHE_MANAGER
import com.ndgndg91.project.global.config.cache.CacheConstants.EXAMPLE_CACHE
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service

@Service
class CacheService {

    @Suppress("FunctionOnlyReturningConstant")
    @Cacheable(cacheNames = [EXAMPLE_CACHE], key = "#root.methodName")
    fun getDefaultCache(): String {
        return "not cached value"
    }

    @Suppress("FunctionOnlyReturningConstant")
    @Cacheable(cacheNames = [EXAMPLE_CACHE], key = "#root.methodName", cacheManager = LOCAL_CACHE_MANAGER)
    fun getLocalCache(): String {
        return "not cached value"
    }

    @Suppress("FunctionOnlyReturningConstant")
    @Cacheable(cacheNames = [EXAMPLE_CACHE], key = "#root.methodName", cacheManager = REDIS_CACHE_MANAGER)
    fun getRedisCache(): String {
        return "not cached value"
    }

    @Suppress("FunctionOnlyReturningConstant")
    @Caching(
        cacheable = [
            Cacheable(cacheNames = [EXAMPLE_CACHE], key = "#root.methodName", cacheManager = LOCAL_CACHE_MANAGER),
            Cacheable(cacheNames = [EXAMPLE_CACHE], key = "#root.methodName", cacheManager = REDIS_CACHE_MANAGER)
        ]
    )
    fun getLayeredCache(): String {
        return "not cached value"
    }
}