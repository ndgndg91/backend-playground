package com.ndgndg91.redisperformance.controller;

import com.ndgndg91.redisperformance.dto.ProductChange;
import com.ndgndg91.redisperformance.entity.Product;
import com.ndgndg91.redisperformance.service.V2ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@RestController
@RequiredArgsConstructor
public class V2ProductController {

    private final V2ProductService service;

    @GetMapping("/v2/apis/products/{id}")
    public Mono<Product> findById(
            @PathVariable final BigInteger id
    ) {
        return service.findById(id);
    }

    @PutMapping("/v2/apis/products/{id}")
    public Mono<Product> update(
            @PathVariable final BigInteger id,
            @RequestBody final ProductChange request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/v2/apis/products/{id}")
    public Mono<Void> delete(
            @PathVariable final BigInteger id
    ) {
        return service.delete(id);
    }


}
