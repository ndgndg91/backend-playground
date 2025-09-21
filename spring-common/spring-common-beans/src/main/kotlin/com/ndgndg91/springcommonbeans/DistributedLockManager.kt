package com.ndgndg91.springcommonbeans

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import java.util.UUID
import java.util.concurrent.TimeUnit

class DistributedLockManager(
    private val redisTemplate: StringRedisTemplate
) {
    companion object {
        // Lua script for safe lock release (unlock).
        // Checks if the key's value matches the expected value before deleting.
        private val UNLOCK_SCRIPT = DefaultRedisScript<Long>(
            """
            if redis.call("get", KEYS[1]) == ARGV[1] then
                return redis.call("del", KEYS[1])
            else
                return 0
            end
            """, Long::class.java
        )
    }

    /**
     * Acquires a distributed lock, executes the action, and safely releases the lock.
     * Retries a few times if the lock is not available.
     *
     * @param E The type of exception to throw on failure.
     * @param key The key for the lock.
     * @param leaseTime The time for which the lock is held.
     * @param timeUnit The time unit for leaseTime.
     * @param retryCount The number of times to retry acquiring the lock.
     * @param retryDelayMillis The delay between retries in milliseconds.
     * @param exceptionSupplier A supplier for the exception to be thrown if the lock cannot be acquired.
     * @param action The action to execute if the lock is acquired.
     * @return The result of the action.
     * @throws E if the lock cannot be acquired after all retries.
     */
    fun <R, E : Throwable> withLock(
        key: String,
        leaseTime: Long,
        timeUnit: TimeUnit,
        retryCount: Int = 3,
        retryDelayMillis: Long = 100,
        exceptionSupplier: () -> E,
        action: () -> R
    ): R {
        val lockKey = "d-lock:$key"
        val lockValue = UUID.randomUUID().toString() // Unique value for this lock attempt
        var acquired = false

        for (i in 1..retryCount) {
            acquired = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, leaseTime, timeUnit) == true
            if (acquired) {
                break
            }
            if (i < retryCount) {
                try {
                    Thread.sleep(retryDelayMillis)
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                    throw exceptionSupplier()
                }
            }
        }

        if (acquired) {
            try {
                return action()
            } finally {
                // Safely release the lock using Lua script
                redisTemplate.execute(UNLOCK_SCRIPT, listOf(lockKey), lockValue)
            }
        } else {
            throw exceptionSupplier()
        }
    }
}
