package com.ndgndg91.redisson.test;

import com.ndgndg91.redisson.test.config.RedissonConfig;
import com.ndgndg91.redisson.test.config.dto.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

/**
 * Config Server
 */
public class Lec08LocalCachedMapTest extends BaseTest {

    private static RLocalCachedMap<Integer, Student> studentsMap;

    @BeforeAll
    static void setupClient() {
        RedissonConfig config = new RedissonConfig();
        RedissonClient redissonClient = config.getClient();

        LocalCachedMapOptions<Integer, Student> mapOptions = LocalCachedMapOptions.<Integer, Student>defaults()
                .syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.NONE);

        studentsMap = redissonClient.getLocalCachedMap(
                "students",
                new TypedJsonJacksonCodec(Integer.class, Student.class),
                mapOptions
        );
    }


    @Test
    void appServer1() {
        // given
        var student1 = new Student("sam", 10, "atlanta", List.of(1,2,3));
        var student2 = new Student("jake", 30, "miami", List.of(10, 20, 30));

        studentsMap.put(1, student1);
        studentsMap.put(2, student2);

        // when
        Flux.interval(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println(i + " ==> " + studentsMap.get(1)))
                .subscribe();

        // then
        sleep(60000);
    }

    @Test
    void appServer2() {
        // given
        var student1 = new Student("sam-updated", 10, "atlanta", List.of(1,2,3));
        studentsMap.put(1, student1);
    }
}
