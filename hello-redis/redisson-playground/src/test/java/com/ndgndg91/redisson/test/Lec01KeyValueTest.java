package com.ndgndg91.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class Lec01KeyValueTest extends BaseTest {

    @Test
    void keyValueAccessTest() {
        // given
        RBucketReactive<String> bucket = client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam");

        // when
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();

        // then
        StepVerifier.create(set.concatWith(get))
                .verifyComplete();
    }

    @Test
    void keyValueExpiryTest() {
        // given
        RBucketReactive<String> bucket = client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam", 10, TimeUnit.SECONDS);

        // when
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();

        // then
        StepVerifier.create(set.concatWith(get))
                .verifyComplete();
    }

    @Test
    void keyValueExtendExpiryTest() {
        // given
        RBucketReactive<String> bucket = client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam", 10, TimeUnit.SECONDS);

        // when
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();

        // then
        StepVerifier.create(set.concatWith(get))
                .verifyComplete();
        //extend
        sleep(5000);
        Mono<Boolean> expire = bucket.expire(Duration.of(60, ChronoUnit.SECONDS));
        StepVerifier.create(expire)
                .expectNext(true)
                .verifyComplete();
        //access
        Mono<Void> ttl = bucket.remainTimeToLive()
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(ttl)
                .verifyComplete();
    }
}
