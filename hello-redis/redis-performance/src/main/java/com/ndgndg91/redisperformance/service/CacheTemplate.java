package com.ndgndg91.redisperformance.service;

import reactor.core.publisher.Mono;

public abstract class CacheTemplate<KEY, ENTITY>{

    public Mono<ENTITY> find(KEY key) {
        return findFromCache(key)
                .switchIfEmpty(findFromSource(key).flatMap(e -> updateCache(key, e)));
    }

    public Mono<ENTITY> update(KEY key, ENTITY entity) {
        return updateSource(key, entity)
                .flatMap(e -> deleteFromCache(key).thenReturn(e));
    }

    public Mono<Void> delete(KEY key) {
        return deleteFromSource(key).then(deleteFromCache(key));
    }

    abstract protected Mono<ENTITY> findFromSource(KEY key);
    abstract protected Mono<ENTITY> findFromCache(KEY key);
    abstract protected Mono<ENTITY> updateSource(KEY key, ENTITY entity);
    abstract protected Mono<ENTITY> updateCache(KEY key, ENTITY entity);
    abstract protected Mono<Void> deleteFromSource(KEY key);
    abstract protected Mono<Void> deleteFromCache(KEY key);
}
