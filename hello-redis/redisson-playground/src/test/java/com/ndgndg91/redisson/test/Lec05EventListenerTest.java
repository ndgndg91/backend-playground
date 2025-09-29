package com.ndgndg91.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.DeletedObjectListener;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeUnit;

public class Lec05EventListenerTest extends BaseTest {

    //127.0.0.1:6379> config set notify-keyspace-events AKE
    //OK
    @Test
    void expiredEventTest() {
        // given
        RBucketReactive<String> bucket = client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("same", 5, TimeUnit.SECONDS);
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();


        // when
        Mono<Void> event = bucket
                .addListener((ExpiredObjectListener) s -> System.out.println("Expired : " + s))
                .then();

        // then
        StepVerifier.create(set.concatWith(get).concatWith(event))
                .verifyComplete();

        sleep(6000);
    }

    // 수동으로 cli 에서 삭제
    // 127.0.0.1:6379> del user:1:name
    // (integer) 1
    @Test
    void deletedEventTest() {
        // given
        RBucketReactive<String> bucket = client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("same");
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();


        // when
        Mono<Void> event = bucket
                .addListener((DeletedObjectListener) s -> System.out.println("Deleted : " + s))
                .then();

        // then
        StepVerifier.create(set.concatWith(get).concatWith(event))
                .verifyComplete();

        sleep(60000);
    }
}
