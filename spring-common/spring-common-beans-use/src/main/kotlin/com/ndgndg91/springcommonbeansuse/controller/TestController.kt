package com.ndgndg91.springcommonbeansuse.controller

import com.ndgndg91.springcommonbeans.ActiveProfileDetector
import com.ndgndg91.springcommonbeans.DistributedLockManager
import com.ndgndg91.springcommonbeans.RedisCacheManager
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit

@RestController
class TestController(
    private val detector: ActiveProfileDetector,
    private val cacheManager: RedisCacheManager,
    private val distributedLockManager: DistributedLockManager
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/api/test")
    fun test(): String {
        if (detector.isProd()) {
            logger.info("Running in production environment")
        }
        return distributedLockManager.withLock(
            "test-lock",
            10,
            TimeUnit.SECONDS,
            exceptionSupplier = { throw RuntimeException("분산락 획득 실패") }
        ) {
            logger.info("Lock acquired")
            cacheManager.getOrSet("key", 10, TimeUnit.SECONDS, String::class.java) {
                Thread.sleep(1500)
                logger.info("Cache miss")
                "test-value"
            }
        }

    }
}