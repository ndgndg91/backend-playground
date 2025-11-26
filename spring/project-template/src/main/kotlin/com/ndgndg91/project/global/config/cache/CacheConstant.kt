package com.ndgndg91.project.global.config.cache

import java.util.concurrent.TimeUnit

enum class Caches(
    val cacheName: String,
    val expireAfterWrite: Long,
    val timeUnit: TimeUnit,
    val maximumSize: Long
) {
    EXAMPLE(CacheConstants.EXAMPLE_CACHE, 60, TimeUnit.MINUTES, 100),
    EXAMPLE2(CacheConstants.EXAMPLE_CACHE2, 60, TimeUnit.MINUTES, 100)
}

object CacheConstants {
    const val EXAMPLE_CACHE = "EXAMPLE:CACHE"
    const val EXAMPLE_CACHE2 = "EXAMPLE:CACHE2"
}