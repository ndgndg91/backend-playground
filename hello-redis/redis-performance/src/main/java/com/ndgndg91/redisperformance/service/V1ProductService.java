package com.ndgndg91.redisperformance.service;

import com.ndgndg91.redisperformance.dto.ProductChange;
import com.ndgndg91.redisperformance.entity.Product;
import com.ndgndg91.redisperformance.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class V1ProductService {

    private final ProductRepository repository;

    public Mono<Product> findById(final BigInteger id) {
        return repository.findById(id);
    }

    public Mono<Product> update(final BigInteger id, final ProductChange change) {
        return repository.findById(id)
                .flatMap(p -> p.update(change))
                .flatMap(repository::save);
    }
}
