package com.ndgndg91.redisperformance.entity;

import com.ndgndg91.redisperformance.dto.ProductChange;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table
public class Product {

    @Id
    @Setter
    private BigInteger id;
    private String description;
    private BigDecimal price;

    public Mono<Product> update(ProductChange change) {
        this.description = change.description();
        this.price = change.price();
        return Mono.just(this);
    }

    public void updateFrom(final ProductChange change) {
        this.description = change.description();
        this.price = change.price();
    }
}
