package com.ndgndg91.redisson.test;

import com.ndgndg91.redisson.test.config.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

public class Lec02KeyValueTest extends BaseTest {

    @Test
    void keyValueObjectTest() {
        // given
        var student = new Student("marshal", 10, "atlanta", List.of(1,2,3));
        RBucketReactive<Student> bucket = client.getBucket("student:1", new TypedJsonJacksonCodec(Student.class));

        // when
        Mono<Void> set = bucket.set(student);
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();

        // then
        StepVerifier.create(set.concatWith(get))
                .verifyComplete();
    }
}
