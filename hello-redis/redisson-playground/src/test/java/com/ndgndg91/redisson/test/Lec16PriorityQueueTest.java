package com.ndgndg91.redisson.test;

import com.ndgndg91.redisson.test.assignment.Category;
import com.ndgndg91.redisson.test.assignment.PriorityQueue;
import com.ndgndg91.redisson.test.assignment.UserOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class Lec16PriorityQueueTest extends BaseTest {

    private static PriorityQueue queue;

    @BeforeAll
    static void setupQueue() {
        queue = new PriorityQueue(client.getScoredSortedSet("user:order:queue", new TypedJsonJacksonCodec(UserOrder.class)));
    }

    @Test
    void producer() {
        Flux.interval(Duration.ofSeconds(1))
                .map(l -> (l.intValue() * 5))
                .doOnNext(i -> {
                    // given
                    UserOrder order1 = new UserOrder(i + 1, Category.GUEST);
                    UserOrder order2 = new UserOrder(i + 2, Category.STD);
                    UserOrder order3 = new UserOrder(i + 3, Category.PRIME);
                    UserOrder order4 = new UserOrder(i + 4, Category.STD);
                    UserOrder order5 = new UserOrder(i + 5, Category.GUEST);


                    // when
                    Mono<Void> mono = Flux.just(order1, order2, order3, order4, order5)
                            .flatMap(queue::add)
                            .then();

                    // then
                    StepVerifier.create(mono)
                            .verifyComplete();
                }).subscribe();

        sleep(60_000);
    }


    @Test
    void consumer() {
        // given - when
        queue.takeItems()
                .delayElements(Duration.ofMillis(500))
                .doOnNext(System.out::println)
                .subscribe();

        // then
        sleep(600_000);


    }
}
