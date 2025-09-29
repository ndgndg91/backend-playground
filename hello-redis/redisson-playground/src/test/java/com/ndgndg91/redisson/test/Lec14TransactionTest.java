package com.ndgndg91.redisson.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.api.RTransactionReactive;
import org.redisson.api.TransactionOptions;
import org.redisson.client.codec.LongCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec14TransactionTest extends BaseTest {

    private static RBucketReactive<Long> user1Balance;
    private static RBucketReactive<Long> user2Balance;

    // user:1:balance 100
    // user:2:balance 0
    @BeforeAll
    static void accountSetup() {
        user1Balance = client.getBucket("user:1:balance", LongCodec.INSTANCE);
        user2Balance = client.getBucket("user:2:balance", LongCodec.INSTANCE);
        Mono<Void> mono = user1Balance.set(100L).then(user2Balance.set(0L)).then();
        StepVerifier.create(mono)
                .verifyComplete();
    }

    @AfterAll
    static void accountBalanceStatus() {
        Mono<Void> mono = Flux.zip(user1Balance.get(), user2Balance.get())
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(mono)
                .verifyComplete();
    }


    @Test
    void nonTransactionTest() {
        // given - when
        transfer(user1Balance, user2Balance, 50)
                .thenReturn(0)
                .map(i -> (5 / i)) // some error
                .doOnError(System.out::println)
                .subscribe();

        // then
        sleep(1000);
    }

    @Test
    void transactionTest() {
        // given
        RTransactionReactive tx = client.createTransaction(TransactionOptions.defaults());
        RBucketReactive<Long> user1Balance = tx.getBucket("user:1:balance", LongCodec.INSTANCE);
        RBucketReactive<Long> user2Balance = tx.getBucket("user:2:balance", LongCodec.INSTANCE);

        // when
        transfer(user1Balance, user2Balance, 50)
                .thenReturn(0)
//                .map(i -> (5 / i)) // some error
                .then(tx.commit())
                .doOnError(System.out::println)
                .onErrorResume(ex -> tx.rollback())
                .subscribe();

        // then
        sleep(1000);
    }

    private Mono<Void> transfer(RBucketReactive<Long> fromAccount, RBucketReactive<Long> toAccount, int amount) {
        return Flux.zip(fromAccount.get(), toAccount.get()) // [b1, b2]
                .filter(t -> t.getT1() >= amount)
                .flatMap(t -> fromAccount.set(t.getT1() - amount).thenReturn(t))
                .flatMap(t -> toAccount.set(t.getT2() + amount))
                .then();
    }
}
