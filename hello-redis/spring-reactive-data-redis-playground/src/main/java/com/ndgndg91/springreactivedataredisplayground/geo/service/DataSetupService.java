package com.ndgndg91.springreactivedataredisplayground.geo.service;

import com.ndgndg91.springreactivedataredisplayground.geo.dto.GeoLocation;
import com.ndgndg91.springreactivedataredisplayground.geo.dto.Restaurant;
import com.ndgndg91.springreactivedataredisplayground.geo.util.RestaurantUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RGeoReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class DataSetupService implements CommandLineRunner {

    private final RGeoReactive<Restaurant> geo;
    private final RMapReactive<String, GeoLocation> map;

    public DataSetupService(RedissonReactiveClient client) {
        this.geo = client.getGeo("restaurants", new TypedJsonJacksonCodec(Restaurant.class));
        this.map = client.getMap("us:texas", new TypedJsonJacksonCodec(String.class, GeoLocation.class));
    }

    @Override
    public void run(String... args) {
        Flux.fromIterable(RestaurantUtil.getRestaurants())
                .flatMap(r -> geo.add(r.getLongitude(), r.getLatitude(), r).thenReturn(r))
                .flatMap(r -> map.fastPut(r.getZip(), new GeoLocation(r.getLongitude(), r.getLatitude())))
                .doFinally(s -> log.info("restaurant added. {}", s))
                .subscribe();
    }
}
