package com.ndgndg91.redisson.test;

import com.ndgndg91.redisson.test.config.RestaurantUtil;
import com.ndgndg91.redisson.test.config.dto.GeoLocation;
import com.ndgndg91.redisson.test.config.dto.Restaurant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.GeoUnit;
import org.redisson.api.RGeoReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.geo.GeoSearchArgs;
import org.redisson.api.geo.OptionalGeoSearch;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

public class Lec17GeoSpatialTest extends BaseTest {

    private static RGeoReactive<Restaurant> geo;
    private static RMapReactive<String, GeoLocation> map;

    @BeforeAll
    static void setGeo() {
        geo = client.getGeo("restaurants", new TypedJsonJacksonCodec(Restaurant.class));
        map = client.getMap("us:texas", new TypedJsonJacksonCodec(String.class, GeoLocation.class));
    }

    @Test
    void add() {
        // given - when
        Mono<Void> mono = Flux.fromIterable(RestaurantUtil.getRestaurants())
                .flatMap(r -> geo.add(r.getLongitude(), r.getLatitude(), r).thenReturn(r))
                .flatMap(r -> map.fastPut(r.getZip(), new GeoLocation(r.getLongitude(), r.getLatitude())))
                .then();

        // then
        StepVerifier.create(mono)
                .verifyComplete();


    }

    /*
        long: -96.80539
        la : 32.78136
    */
    @Test
    void searchByLongitudeLatitude() {
        // given
        OptionalGeoSearch radius = GeoSearchArgs.from(-96.80539, 32.78136).radius(3, GeoUnit.MILES);

        // when
        Mono<Void> mono = geo.search(radius)
                .flatMapIterable(Function.identity())
                .doOnNext(System.out::println)
                .then();

        // then
        StepVerifier.create(mono)
                .verifyComplete();
    }

    @Test
    void searchByZip() {
        // given - when
        Mono<Void> mono = map.get("75224")
                .map(gl -> GeoSearchArgs.from(gl.getLongitude(), gl.getLatitude()).radius(5, GeoUnit.MILES))
                .flatMap(r -> geo.search(r))
                .flatMapIterable(Function.identity())
                .doOnNext(System.out::println)
                .then();

        // then
        StepVerifier.create(mono)
                .verifyComplete();
    }


}
