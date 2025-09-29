package com.ndgndg91.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.RDequeReactive;
import org.redisson.api.RListReactive;
import org.redisson.api.RQueueReactive;
import org.redisson.client.codec.LongCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Lec09ListQueueStackTest extends BaseTest {

    @Test
    void listTest() {
        // lrange number-input 0 -1
        // given
        RListReactive<Long> list = client.getList("number-input", LongCodec.INSTANCE);

        // when
        List<Long> longList = LongStream.range(0, 10)
                .boxed()
                .collect(Collectors.toList());

        // then
        StepVerifier.create(list.addAll(longList).then())
                .verifyComplete();
        StepVerifier.create(list.size())
                .expectNext(10)
                .verifyComplete();
    }

    @Test
    void queueTest() {
        // given
        RQueueReactive<Long> queue = client.getQueue("number-input", LongCodec.INSTANCE);

        // when
        Mono<Void> poll = queue.poll().repeat(3).doOnNext(System.out::println).then();

        // then
        StepVerifier.create(poll)
                .verifyComplete();
        StepVerifier.create(queue.size())
                .expectNext(6)
                .verifyComplete();
    }

    @Test
    void stackTest() { //Dequeue
        // given
        RDequeReactive<Object> stack = client.getDeque("number-input", LongCodec.INSTANCE);

        // when
        Mono<Void> pop = stack.pollLast().repeat(3).doOnNext(System.out::println).then();

        // then
        StepVerifier.create(pop)
                .verifyComplete();
        StepVerifier.create(stack.size())
                .expectNext(2)
                .verifyComplete();
    }
}
