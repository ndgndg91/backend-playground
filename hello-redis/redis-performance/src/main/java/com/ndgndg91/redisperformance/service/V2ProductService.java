package com.ndgndg91.redisperformance.service;

import com.ndgndg91.redisperformance.dto.ProductChange;
import com.ndgndg91.redisperformance.entity.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Service
public class V2ProductService {

    private final CacheTemplate<BigInteger, Product> template;

    private final ProductVisitService service;

    public V2ProductService(ProductCacheTemplate template, ProductVisitService service) {
        this.template = template;
        this.service = service;
    }

    public Mono<Product> findById(final BigInteger id) {
        return template.find(id)
                .doFirst(() -> service.addVisit(id));
    }

    public Mono<Product> update(final BigInteger id, final ProductChange change) {
        var product = new Product();
        product.setId(id);
        product.updateFrom(change);
        return template.update(id, product);
    }

    public Mono<Void> delete(final BigInteger id) {
        return template.delete(id);
    }
}
