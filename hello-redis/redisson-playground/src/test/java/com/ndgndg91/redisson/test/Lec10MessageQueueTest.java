package com.ndgndg91.redisson.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBlockingDequeReactive;
import org.redisson.client.codec.LongCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

// one message per on consumer
public class Lec10MessageQueueTest extends BaseTest {
    private static RBlockingDequeReactive<Long> messageQueue;

    @BeforeAll
    static void setupQueue() {
        // using list
        messageQueue = client.getBlockingDeque("message-queue", LongCodec.INSTANCE);
    }

    @Test
    void consumer1() {
        // given
        messageQueue.takeElements()
                .doOnNext(i -> System.out.println("Consumer 1 : " + i))
                .doOnError(System.out::println)
                .subscribe();

        // when - then
        sleep(600_000);
    }


    @Test
    void consumer2() {
        // given
        messageQueue.takeElements()
                .doOnNext(i -> System.out.println("Consumer 2 : " + i))
                .doOnError(System.out::println)
                .subscribe();

        // when - then
        sleep(600_000);
    }

    @Test
    void producer1() {
        // given
        Mono<Void> mono = Flux.range(0, 100)
                .delayElements(Duration.ofMillis(500))
                .doOnNext(i -> System.out.println("going to add " + i))
                .flatMap(i -> messageQueue.add(Long.valueOf(i)))
                .then();

        // when - then
        StepVerifier.create(mono)
                .verifyComplete();
    }

}
