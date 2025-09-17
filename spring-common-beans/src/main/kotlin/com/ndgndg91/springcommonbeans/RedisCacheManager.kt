package com.ndgndg91.springcommonbeans

import org.springframework.data.redis.core.StringRedisTemplate
import java.util.concurrent.TimeUnit

class RedisCacheManager(
    private val redisTemplate: StringRedisTemplate
) {
    fun <R : Any> getOrSet(
        key: String,
        timeout: Long,
        timeUnit: TimeUnit,
        clazz: Class<R>,
        action: () -> R
    ): R {
        return redisTemplate.opsForValue().get(key)?.toObj(clazz)
            ?: action().apply {
                redisTemplate.opsForValue().set(key, toJson(), timeout, timeUnit)
            }
    }

    fun <R> writeAndInvalidate(key: String, action: () -> R): R {
        return action().apply {
            invalidate(key)
        }
    }

    fun <R> writeAndInvalidates(keys: List<String>, action: () -> R): R {
        return action().apply {
            invalidates(keys)
        }
    }

    fun invalidate(key: String) {
        redisTemplate.delete(key)
    }

    fun invalidates(keys: List<String>) {
        redisTemplate.delete(keys)
    }
}