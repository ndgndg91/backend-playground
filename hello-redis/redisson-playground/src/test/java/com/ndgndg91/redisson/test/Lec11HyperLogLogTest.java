package com.ndgndg91.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.RHyperLogLogReactive;
import org.redisson.client.codec.LongCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Lec11HyperLogLogTest extends BaseTest {

    // 12.5.kb max size
    @Test
    void count() {
        // given
        RHyperLogLogReactive<Long> counter = client.getHyperLogLog("user:visits", LongCodec.INSTANCE);

        // when
        List<Long> longList = LongStream.rangeClosed(1, 25_000)
                .boxed()
                .collect(Collectors.toList());

        List<Long> longList2 = LongStream.rangeClosed(25_001, 50_000)
                .boxed()
                .collect(Collectors.toList());

        List<Long> longList3 = LongStream.rangeClosed(50_001, 75_000)
                .boxed()
                .collect(Collectors.toList());

        List<Long> longList4 = LongStream.rangeClosed(75_001, 100_000)
                .boxed()
                .collect(Collectors.toList());

        Mono<Void> mono = Flux.just(longList, longList2, longList3, longList4)
                .flatMap(counter::addAll)
                .then();

        // then
        StepVerifier.create(mono)
                .verifyComplete();

        counter.count()
                .doOnNext(System.out::println)
                .subscribe();
    }
}
