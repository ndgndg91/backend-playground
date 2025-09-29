package com.ndgndg91.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec04BucketAsMApTest extends BaseTest {

    // user:1:name
    // user:2:name
    // user:3:name
    @Test
    void bucketAsMap() {
        // given - when
        Mono<Void> mono = client.getBuckets(StringCodec.INSTANCE)
                .get("user:1:name", "user:2:name", "user:3:name", "user:4:name")
                .doOnNext(System.out::println)
                .then();


        // then
        StepVerifier.create(mono)
                .verifyComplete();
    }
}
