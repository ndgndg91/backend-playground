package com.ndgndg91.redisperformance.service;

import com.ndgndg91.redisperformance.entity.Product;
import com.ndgndg91.redisperformance.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {

    private final ProductRepository repository;

    private final R2dbcEntityTemplate template;

    @Value("classpath:schema.sql")
    private Resource resource;

    @Override
    public void run(String... args) throws Exception {
        final var sql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        log.info("sql : {}", sql);

        Mono<Void> insert = Flux.range(1, 1000)
                .map(i -> new Product(null, "product " + i, BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(100, 1000))))
                .collectList()
                .flatMapMany(repository::saveAll)
                .then();

        this.template.getDatabaseClient()
                .sql(sql)
                .then()
                .then(insert )
                .doFinally(s -> log.info("data setup done. {}", s))
                .subscribe();
    }

}
