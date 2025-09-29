package com.ndgndg91.redisperformance.service;

import org.redisson.api.BatchOptions;
import org.redisson.api.RBatchReactive;
import org.redisson.api.RScoredSortedSetReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.LongCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductVisitService {

    private final RedissonReactiveClient client;
    private final Sinks.Many<BigInteger> sink;

    public ProductVisitService(RedissonReactiveClient client) {
        this.client = client;
        this.sink = Sinks.many().unicast().onBackpressureBuffer();
    }

    @PostConstruct
    private void init() {
        sink.asFlux()
                .buffer(Duration.ofSeconds(3))
                .map(l -> l.stream().collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                ))
                .flatMap(this::updateBatch)
                .subscribe();
    }

    public void addVisit(BigInteger productId) {
         sink.tryEmitNext(productId);
    }

    private Mono<Void> updateBatch(Map<BigInteger, Long> map) {
        RBatchReactive batch = client.createBatch(BatchOptions.defaults());
        String format = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
        RScoredSortedSetReactive<BigInteger> set = batch.getScoredSortedSet("product:visit:" + format, LongCodec.INSTANCE);

        return Flux.fromIterable(map.entrySet())
                .map(e -> set.addScore(e.getKey(), e.getValue()))
                .then(batch.execute())
                .then();
    }
}
