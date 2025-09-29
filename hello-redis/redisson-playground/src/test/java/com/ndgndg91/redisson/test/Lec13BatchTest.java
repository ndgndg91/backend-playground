package com.ndgndg91.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.BatchOptions;
import org.redisson.api.RBatchReactive;
import org.redisson.api.RListReactive;
import org.redisson.api.RSetReactive;
import org.redisson.client.codec.LongCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec13BatchTest extends BaseTest {

    // 1.147 sec
    @Test
    void batchTest() {
        // given
        RBatchReactive batch = client.createBatch(BatchOptions.defaults());
        RListReactive<Long> list = batch.getList("numbers-list", LongCodec.INSTANCE);
        RSetReactive<Long> set = batch.getSet("numbers-set", LongCodec.INSTANCE);

        // when
        for (long i = 0; i < 50_000; i++) {
            list.add(i);
            set.add(i);
        }

        // then
        StepVerifier.create(batch.execute().then())
                .verifyComplete();
    }

    // 12.659 sec
    @Test
    void regularTest() {
        // given
        RListReactive<Long> list = client.getList("numbers-list-regular", LongCodec.INSTANCE);
        RSetReactive<Long> set = client.getSet("numbers-set-regular", LongCodec.INSTANCE);

        // when
        Mono<Void> mono = Flux.range(1, 500_000)
                .map(Long::valueOf)
                .flatMap(i -> list.add(i).then(set.add(i)))
                .then();

        // then
        StepVerifier.create(mono)
                .verifyComplete();
    }
}
