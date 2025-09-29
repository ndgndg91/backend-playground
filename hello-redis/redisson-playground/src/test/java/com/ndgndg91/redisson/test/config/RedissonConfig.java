package com.ndgndg91.redisson.test.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;

import java.util.Objects;

public class RedissonConfig {

    private RedissonClient client;

    public RedissonClient getClient() {
        if (Objects.isNull(this.client)) {
            Config config = new Config();
            config.useSingleServer()
                    .setAddress("redis://127.0.0.1:6379");
            client = Redisson.create(config);
        }

        return client;
    }

    public RedissonReactiveClient getReactiveClient() {
        return getClient().reactive();
    }
}
