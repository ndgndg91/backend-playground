package com.ndgndg91.springreactivedataredisplayground.fib.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FibService {

    //127.0.0.1:6379> KEYS *
    //1) "math:fib"
    //127.0.0.1:6379> type math:fib
    //hash
    //127.0.0.1:6379> HGETALL math:fib
    //1) "\x04L\x00\x00\x00\x00\x00\x00\x00-"
    //2) "\x04L\x00\x00\x00\x00C\xa5?\x82"
    // have a strategy for cache evict
    @Cacheable(value = "math:fib", key = "#index")
    public long getFib(long index) {
        log.info("calculating fib for {}", index);
        return fib(index);
    }

    // PUT / POST / PATCH / DELETE
    @CacheEvict(value = "math:fib", key = "#index")
    public void clearCache(long index) {
        log.info("clearing hash key");
    }

    @Scheduled(fixedDelay = 10_000)
    @CacheEvict(value = "math:fib", allEntries = true)
    public void clearCache() {
        log.info("clearing all fib keys");
    }

    // intentional 2^N
    private long fib(long index) {
        if (index < 2) return index;

        return fib(index - 1) + fib(index - 2);
    }
}
