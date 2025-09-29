package com.ndgndg91.redisperformance.service;

import com.ndgndg91.redisperformance.entity.Product;
import com.ndgndg91.redisperformance.repository.ProductRepository;
import org.redisson.api.*;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Service
public class ProductLocalCacheTemplate extends CacheTemplate<BigInteger, Product> {

    private final ProductRepository repository;
    private final RLocalCachedMap<BigInteger, Product> map;

    public ProductLocalCacheTemplate(ProductRepository repository, RedissonClient client) {
        this.repository = repository;
        LocalCachedMapOptions<BigInteger, Product> options = LocalCachedMapOptions.<BigInteger, Product>defaults()
                .syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.CLEAR);
        this.map = client.getLocalCachedMap("product-local-cache-map", new TypedJsonJacksonCodec(BigInteger.class, Product.class), options);
    }

    @Override
    protected Mono<Product> findFromSource(BigInteger id) {
        return repository.findById(id);
    }

    @Override
    protected Mono<Product> findFromCache(BigInteger id) {
        return Mono.justOrEmpty(map.get(id));
    }

    @Override
    protected Mono<Product> updateSource(BigInteger id, Product product) {
        return repository.findById(id)
                .doOnNext(p -> product.setId(id))
                .flatMap(p -> repository.save(product));
    }

    @Override
    protected Mono<Product> updateCache(BigInteger id, Product product) {
        return Mono.create(
                sink -> map.fastPutAsync(id, product)
                        .thenAccept(b -> sink.success(product))
                        .exceptionally(ex -> {
                            sink.error(ex);
                            return null;
                        })
        );
    }

    @Override
    protected Mono<Void> deleteFromSource(BigInteger id) {
        return repository.deleteById(id);
    }

    @Override
    protected Mono<Void> deleteFromCache(BigInteger id) {
        return Mono.create(sink -> map.fastRemoveAsync(id)
                .thenAccept(b -> sink.success())
                .exceptionally(ex -> {
                    sink.error(ex);
                    return null;
                })
        );
    }
}
