package com.ndgndg91.redisson.test;

import com.ndgndg91.redisson.test.config.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapReactive;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

public class Lec06MapTest extends BaseTest {

    //127.0.0.1:6379> HGETALL user:1
    //1) "name"
    //2) "sam"
    //3) "age"
    //4) "10"
    //5) "city"
    //6) "seoul"
    //127.0.0.1:6379> HGET user:1 age
    //"10"
    @Test
    void mapTest() {
        // given
        RMapReactive<String, String> map = client.getMap("user:1", StringCodec.INSTANCE);

        // when
        Mono<String> name = map.put("name", "sam");
        Mono<String> age = map.put("age", "10");
        Mono<String> city = map.put("city", "seoul");

        // then
        StepVerifier.create(name.concatWith(age).concatWith(city))
                .verifyComplete();
    }

    //127.0.0.1:6379> HGETALL user:2
    //1) "city"
    //2) "miami"
    //3) "name"
    //4) "jake"
    //5) "age"
    //6) "25"
    //127.0.0.1:6379> HGET user:2 age
    //"25"
    @Test
    void mapTest2() {
        // given
        RMapReactive<String, String> map = client.getMap("user:2", StringCodec.INSTANCE);
        Map<String, String> javaMap = Map.of(
                "name", "jake",
                "age", "25",
                "city", "miami"
        );

        // when
        Mono<Void> putAll = map.putAll(javaMap).then();

        // then
        StepVerifier.create(putAll)
                .verifyComplete();
    }

    //127.0.0.1:6379> HGETALL users
    //1) "1"
    //2) "{\"age\":10,\"city\":\"seoul\",\"marks\":[1,2,3],\"name\":\"sam\"}"
    //3) "2"
    //4) "{\"age\":25,\"city\":\"miami\",\"marks\":[2,3,4],\"name\":\"jake\"}"
    //127.0.0.1:6379> HGET users 1
    //"{\"age\":10,\"city\":\"seoul\",\"marks\":[1,2,3],\"name\":\"sam\"}"
    //127.0.0.1:6379> HGET users 2
    //"{\"age\":25,\"city\":\"miami\",\"marks\":[2,3,4],\"name\":\"jake\"}"
    @Test
    void mapTest3() {
        // given
        TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
        RMapReactive<Integer, Student> map = client.getMap("users", codec);

        // when
        Student sam = new Student("sam", 10, "seoul", List.of(1,2,3));
        Student jake = new Student("jake", 25, "miami", List.of(2,3,4));
        Mono<Student> putSam = map.put(1, sam);
        Mono<Student> putJake = map.put(2, jake);

        // then
        StepVerifier.create(putSam.concatWith(putJake))
                .verifyComplete();
    }
}
