# Spring Webflux data redis playground
### spring-data-redis-reactive vs redisson-spring-boot-starter

- Spring Data Reactive Redis
  - Performance Issues
  - No Support for Reactive CRUD repository
  - Some annotations do not work with reactive type!

## User Case 1 : Caching
- Reduce Load on DB
  - Avoid Same query re-execution
- Avoid Tons of Network calls
  - Storing API responses
- Avoid Rework
  - Storing the complex computation results.
- Better Throughput
- Improved application response time
- Better user experience
### Cache Aside Pattern
- Primary Use Case
  - Caching Responses
  - To reduce load on the DB
  - To reduce external API calls
- Why app itself can not cache?
  - Waste of memory
  - Cache synchronization
1. Read from cache
2. If not, it from DB / Compute
3. Save it in cache for future use
4. Return the result

### @Cacheable / @CacheEvict / @CachePut
- @Cacheable
  - skip the method execution if key is present
  - do the method execution only if the key is not present & store the result
- @CacheEvict
  - do the method execution always & evict the corresponding cache
  - evict happens after method execution. (use beforeInvocation property otherwise)
- @CachePut
  - do the method execution always & update the corresponding cache

### Cache Annotations
- easy to use
- limitations
  - no ttl support
  - not a lot of flexibility
  - does not work with publisher types!!