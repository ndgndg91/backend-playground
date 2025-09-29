package com.ndgndg91.redisperformance.service;

import com.ndgndg91.redisperformance.entity.Product;
import com.ndgndg91.redisperformance.repository.ProductRepository;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Service
public class ProductCacheTemplate extends CacheTemplate<BigInteger, Product> {

    private final ProductRepository repository;
    private final RMapReactive<BigInteger, Product> map;

    public ProductCacheTemplate(ProductRepository repository, RedissonReactiveClient client) {
        this.repository = repository;
        this.map = client.getMap("product", new TypedJsonJacksonCodec(BigInteger.class, Product.class));
    }

    @Override
    protected Mono<Product> findFromSource(BigInteger id) {
        return repository.findById(id);
    }

    @Override
    protected Mono<Product> findFromCache(BigInteger id) {
        return map.get(id);
    }

    @Override
    protected Mono<Product> updateSource(BigInteger id, Product product) {
        return repository.findById(id)
                .doOnNext(p -> product.setId(id))
                .flatMap(p -> repository.save(product));
    }

    @Override
    protected Mono<Product> updateCache(BigInteger id, Product product) {
        return map.fastPut(id, product).thenReturn(product);
    }

    @Override
    protected Mono<Void> deleteFromSource(BigInteger id) {
        return repository.deleteById(id);
    }

    @Override
    protected Mono<Void> deleteFromCache(BigInteger id) {
        return map.fastRemove(id).then();
    }
}
