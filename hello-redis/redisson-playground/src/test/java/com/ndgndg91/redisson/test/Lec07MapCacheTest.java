package com.ndgndg91.redisson.test;

import com.ndgndg91.redisson.test.config.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapCacheReactive;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lec07MapCacheTest extends BaseTest {

    @Test
    void mapCacheTest() {
        // given
        TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
        RMapCacheReactive<Integer, Student> mapCache = client.getMapCache("users:cache", codec);

        // when
        Student sam = new Student("sam", 10, "seoul", List.of(1, 2, 3));
        Student jake = new Student("jake", 25, "miami", List.of(2, 3, 4));
        Mono<Student> putSam = mapCache.put(1, sam, 5, TimeUnit.SECONDS);
        Mono<Student> putJake = mapCache.put(2, jake, 5, TimeUnit.SECONDS);

        // then
        StepVerifier.create(putSam.concatWith(putJake).then())
                .verifyComplete();
        sleep(3000);

        // access
        mapCache.get(1).doOnNext(System.out::println).subscribe();
        mapCache.get(2).doOnNext(System.out::println).subscribe();

        sleep(3000);

        // access
        mapCache.get(1).doOnNext(System.out::println).subscribe();
        mapCache.get(2).doOnNext(System.out::println).subscribe();
    }
}
