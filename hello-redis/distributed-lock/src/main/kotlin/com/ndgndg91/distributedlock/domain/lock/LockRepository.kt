package com.ndgndg91.distributedlock.domain.lock

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class LockRepository(
    private val redisTemplate: StringRedisTemplate
) {

    fun acquireLock(lockKey: String, identifier: String, expireSeconds: Long): Boolean {
        val success = redisTemplate.opsForValue().setIfAbsent(lockKey, identifier, Duration.ofSeconds(expireSeconds))
        return success != null && success
    }

    fun releaseLock(lockKey: String, identifier: String) {
        val currentValue = redisTemplate.opsForValue()[lockKey]
        if (identifier == currentValue) {
            redisTemplate.delete(lockKey)
        }
    }
}