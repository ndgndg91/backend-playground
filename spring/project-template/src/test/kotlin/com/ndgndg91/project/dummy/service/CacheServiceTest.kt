package com.ndgndg91.project.dummy.service

import com.ndgndg91.project.AbstractIntegrationTest
import com.ndgndg91.project.global.config.cache.CacheConstants.EXAMPLE_CACHE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class CacheServiceTest: AbstractIntegrationTest() {

    @Autowired
    lateinit var caffeineCacheManager: CaffeineCacheManager

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>

    @Autowired
    lateinit var cacheService: CacheService

    @BeforeEach
    fun setUp() {
        redisTemplate.delete(redisTemplate.keys("*"))
        caffeineCacheManager.getCache(EXAMPLE_CACHE)!!.clear()

    }

    @DisplayName("@Cacheable 테스트")
    @Nested
    inner class CacheableAnnotationTest {
        @DisplayName("localCache 존재시 캐시 조회에 성공해야 한다.")
        @Test
        fun should_return_cached_value_when_cache_exists() {
            // given
            val cachedValue = "cached value"
            caffeineCacheManager.getCache(EXAMPLE_CACHE)!!.put("getDefaultCache", cachedValue)

            // when
            val result = cacheService.getDefaultCache()

            // then
            assertThat(result).isEqualTo(cachedValue)
        }

        @DisplayName("캐시 미스시, 로컬 캐시가 업데이트 되어야한다")
        @Test
        fun should_update_local_cache_when_cache_miss() {
            // given
            val notCachedValue = cacheService.getDefaultCache()
            Assertions.assertEquals("not cached value", notCachedValue)

            // when
            val result = caffeineCacheManager.getCache(EXAMPLE_CACHE)!!.get("getDefaultCache", String::class.java)

            // then
            assertThat(result).isEqualTo(notCachedValue)
        }

        @DisplayName("redis 캐시만 존재시, @Cacheable 캐시 미스") // 지정 없이 사용할 경우 우선순위가 높은 캐시만 조회된다.
        @Test
        fun should_return_not_cached_value_when_only_redis_cache_exists() {
            // given
            redisTemplate.opsForValue().set("EXAMPLE:CACHE::getDefaultCache", "cached value")

            // when
            val result = cacheService.getDefaultCache()

            // then
            assertThat(result).isEqualTo("not cached value")
        }

        @DisplayName("동시에 접근하였을 경우에도 캐시가 정확히 반환 된다")
        @Test
        fun should_return_same_value_when_access_concurrently() {
            // given
            val expectedValue = "updated value"
            caffeineCacheManager.getCache(EXAMPLE_CACHE)!!.put("getDefaultCache", expectedValue)
            val executor = Executors.newFixedThreadPool(10)
            val tasks = (1..10).map {
                Callable {
                    cacheService.getDefaultCache()
                }
            }

            // when
            val results = executor.invokeAll(tasks)

            // then
            results.forEach {
                assertThat(it.get()).isEqualTo(expectedValue)
            }
            executor.shutdown()
        }
    }

    @DisplayName("@Caching 테스트")
    @Nested
    inner class LayeredCacheTest {
        @DisplayName("캐시 미스시, 로컬 캐시가 업데이트 되어야한다")
        @Test
        fun should_update_local_cache_when_cache_miss() {
            // when
            val result = cacheService.getLayeredCache()
            Assertions.assertEquals("not cached value", result)

            // then
            val localCachedValue =
                caffeineCacheManager.getCache(EXAMPLE_CACHE)!!.get("getLayeredCache", String::class.java)
            assertThat(localCachedValue).isEqualTo(result)
        }

        @DisplayName("로컬 캐시가 존재할 경우, 캐시 조회 성공")
        @Test
        fun should_return_cached_value_when_local_cache_exists() {
            // given
            val localCachedValue = "local cached value"
            caffeineCacheManager.getCache(EXAMPLE_CACHE)!!.put("getLayeredCache", localCachedValue)

            // when
            val result = cacheService.getLayeredCache()

            // then
            assertThat(result).isEqualTo(localCachedValue)
        }

        @DisplayName("redis 캐시만 존재할 경우, 조회 성공")
        @Test
        fun should_return_cached_value_when_only_redis_cache_exists() {
            // given
            val redisCachedValue = "redis cached value"
            redisTemplate.opsForValue().set("EXAMPLE:CACHE::getLayeredCache", redisCachedValue)

            // when
            val result = cacheService.getLayeredCache()

            // then
            assertThat(result).isEqualTo(redisCachedValue)
        }

        @DisplayName("redis 캐시만 존재할 경우, 로컬 캐시는 업데이트 되지 않는다")
        @Test
        fun should_not_update_local_cache_when_only_redis_cache_exists() {
            // given
            val redisCachedValue = "redis cached value"
            redisTemplate.opsForValue().set("EXAMPLE:CACHE::getLayeredCache", redisCachedValue)

            // when
            val result = cacheService.getLayeredCache()
            assertThat(result).isEqualTo(redisCachedValue)

            // then
            val localCachedValue =
                caffeineCacheManager.getCache(EXAMPLE_CACHE)!!.get("getLayeredCache", String::class.java)
            assertThat(localCachedValue).isNull()
        }

        @DisplayName("로컬 캐시만 존재할 경우, redis는 캐시는 업데이트 되지 않는다")
        @Test
        fun should_not_update_redis_cache_when_local_cache_exists() {
            // given
            val localCachedValue = "local cached value"
            caffeineCacheManager.getCache(EXAMPLE_CACHE)!!.put("getLayeredCache", localCachedValue)

            // when
            val result = cacheService.getLayeredCache()
            assertThat(result).isEqualTo(localCachedValue)

            // then
            val redisCache = redisTemplate.opsForValue().get("EXAMPLE:CACHE::getLayeredCache")
            assertThat(redisCache).isNull()
        }
    }

}