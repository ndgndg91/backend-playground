package com.ndgndg91.project.global.config.cache

import com.ndgndg91.project.AbstractIntegrationTest
import com.ndgndg91.project.global.config.cache.CacheConstants.EXAMPLE_CACHE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.core.RedisTemplate

class CacheConfigTest: AbstractIntegrationTest() {

    @Autowired
    lateinit var cacheManager: CacheManager

    @Autowired
    lateinit var caffeineCacheManager: CaffeineCacheManager

    @Autowired
    lateinit var redisCacheManager: RedisCacheManager

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>

    @Nested
    inner class DefaultCacheManager {
        @BeforeEach
        fun setUp() {
            cacheManager.getCache(EXAMPLE_CACHE)!!.clear()
        }

        @DisplayName("Cache 조회 성공 테스트")
        @Test
        fun should_return_cached_value_when_cache_exists() {
            // given
            val cache = cacheManager.getCache(EXAMPLE_CACHE)
            val cachedValue = "value"
            cache!!.put("key", cachedValue)

            // when
            val result = cache.get("key", String::class.java)

            // then
            assertThat(result).isEqualTo(cachedValue)
        }

        @DisplayName("다른 캐시 네임스페이스의 캐시 충돌 없이 작동해야 한다")
        @Test
        fun should_work_without_cache_collision() {
            // given
            val firstCache = "first cache value"
            val secondCache = "second cache value"

            cacheManager.getCache("first")!!.put("key", firstCache)
            cacheManager.getCache("second")!!.put("key", secondCache)

            // when
            val firstResult = cacheManager.getCache("first")!!.get("key", String::class.java)
            val secondResult = cacheManager.getCache("second")!!.get("key", String::class.java)

            // then
            assertThat(firstResult).isEqualTo(firstCache)
            assertThat(secondResult).isEqualTo(secondCache)
        }

        @DisplayName("캐시 삭제하면 null을 반환한다")
        @Test
        fun should_return_null_when_cache_evicted() {
            // given
            val cache = cacheManager.getCache(EXAMPLE_CACHE)
            val cachedValue = "value"
            cache!!.put("key", cachedValue)

            // when
            cache.evict("key")
            val result = cache.get("key", String::class.java) // 캐시 미스

            // then
            assertThat(result).isNull()
        }

        @DisplayName("Caches enum의 모든 캐시가 등록되어 있어야한다.")
        @Test
        fun should_register_all_caches() {
            Caches.entries.forEach {
                val cache = cacheManager.getCache(it.cacheName)
                assertThat(cache).isNotNull
            }
        }
    }

    @Nested
    inner class CaffeineCacheManagerTest {
        @BeforeEach
        fun setUp() {
            caffeineCacheManager.getCache(EXAMPLE_CACHE)!!.clear()
        }

        @DisplayName("조회 성공 테스트")
        @Test
        fun should_return_cached_value_when_cache_exists() {
            // given
            val cache = caffeineCacheManager.getCache(EXAMPLE_CACHE)
            val cachedValue = "value"
            cache!!.put("key", cachedValue)

            // when
            val result = cache.get("key", String::class.java)

            // then
            assertThat(result).isEqualTo(cachedValue)
        }

        @DisplayName("Cache 삭제하면 null을 반환한다")
        @Test
        fun should_return_null_when_cache_evicted() {
            // given
            val cache = cacheManager.getCache(EXAMPLE_CACHE)
            val cachedValue = "value"
            cache!!.put("key", cachedValue)

            // when
            cache.evict("key")
            val result = cache.get("key", String::class.java) // 캐시 미스

            // then
            assertThat(result).isNull()
        }

        @DisplayName("Caches enum의 모든 캐시가 등록되어 있어야한다.")
        @Test
        fun should_register_all_caches() {
            Caches.entries.forEach {
                val cache = caffeineCacheManager.getCache(it.cacheName)
                assertThat(cache).isNotNull
            }
        }
    }

    @Nested
    inner class RedisCacheManagerTest {
        @BeforeEach
        fun setUp() {
            redisCacheManager.getCache(EXAMPLE_CACHE)!!.clear()
        }

        @DisplayName("Redis에 직접 입력한 key를 CacheManager 에서 가져올 수 있다")
        @Test
        fun should_return_value_when_redis_key_exists() {
            // given
            val key = "key"
            val value = "value"
            redisTemplate.opsForValue().set("$EXAMPLE_CACHE::$key", value)

            // when
            val result = redisCacheManager.getCache(EXAMPLE_CACHE)!!.get(key, String::class.java)

            // then
            assertThat(result).isEqualTo(value)
        }


        @DisplayName("Redis Cache 조회 성공 테스트")
        @Test
        fun should_return_cached_value_when_cache_exists() {
            // given
            val cache = redisCacheManager.getCache(EXAMPLE_CACHE)
            val cachedValue = "value"
            cache!!.put("key", cachedValue)

            // when
            val result = cache.get("key", String::class.java)

            // then
            Assertions.assertEquals(cachedValue, result)
        }

        @DisplayName("Redis Cache 삭제하면 null을 반환한다")
        @Test
        fun should_return_null_when_cache_evicted() {
            // given
            val cache = redisCacheManager.getCache(EXAMPLE_CACHE)
            val cachedValue = "value"
            cache!!.put("key", cachedValue)

            // when
            cache.evict("key")
            val result = cache.get("key", String::class.java) // 캐시 미스

            // then
            Assertions.assertNull(result)
        }
    }

}