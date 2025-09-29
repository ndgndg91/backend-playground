package com.ndgndg91.springreactivedataredisplayground.city.service;

import com.ndgndg91.springreactivedataredisplayground.city.City;
import com.ndgndg91.springreactivedataredisplayground.city.repository.CityRepository;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityRepository repository;

    private final RMapCacheReactive<BigInteger, City> cacheMap;

    private final RMapReactive<BigInteger, City> map;

    public CityService(CityRepository repository, RedissonReactiveClient client) {
        this.repository = repository;
        this.cacheMap = client.getMapCache("city-cache-map", new TypedJsonJacksonCodec(BigInteger.class, City.class));
        this.map = client.getMap("city-map", new TypedJsonJacksonCodec(BigInteger.class, City.class));
    }

    public Mono<City> findCity(final BigInteger id) {
        return cacheMap.get(id).switchIfEmpty(
                repository.findCityById(id).flatMap(c -> cacheMap.fastPut(id, c, 10, TimeUnit.SECONDS).thenReturn(c))
        );
    }

    public Mono<City> findCityById(final BigInteger id) {
        return map.get(id).onErrorResume(ex -> repository.findCityById(id));
    }

    public Flux<City> findAll() {
        return repository.findAllCities().flatMap(cr -> Flux.fromIterable(cr.data()));
    }

    @Scheduled(fixedDelay = 10_000)
    public void refresh() {
        repository.findAllCities()
                .map(cr -> cr.data().stream().collect(Collectors.toMap(City::id, Function.identity())))
                .flatMap(map::putAll)
                .log()
                .subscribe();
    }
}
