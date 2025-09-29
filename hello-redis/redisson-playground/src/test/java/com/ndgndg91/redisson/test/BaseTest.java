package com.ndgndg91.redisson.test;

import com.ndgndg91.redisson.test.config.RedissonConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.redisson.api.RedissonReactiveClient;

public class BaseTest {

    private static final RedissonConfig redissonConfig = new RedissonConfig();
    protected static RedissonReactiveClient client;

    @BeforeAll
    public static void setClient() {
        client = redissonConfig.getReactiveClient();
    }

    @AfterAll
    public static void shutdown() {
        client.shutdown();
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
