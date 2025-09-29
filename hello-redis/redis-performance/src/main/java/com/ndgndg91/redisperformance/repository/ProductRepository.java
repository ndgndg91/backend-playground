package com.ndgndg91.redisperformance.repository;

import com.ndgndg91.redisperformance.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, BigInteger> {
}
