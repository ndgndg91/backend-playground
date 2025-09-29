package com.ndgndg91.springreactivedataredisplayground.city.repository;

import com.ndgndg91.springreactivedataredisplayground.city.City;
import com.ndgndg91.springreactivedataredisplayground.city.CityServiceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public class CityRepository {

    private final WebClient client;

    public CityRepository(@Value("${city.service.url}") final String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<City> findCityById(final BigInteger id) {
        return client.get()
                .uri("/apis/cities/{id}", id)
                .retrieve()
                .bodyToMono(City.class)
                .log();
    }

    public Flux<CityServiceResponse> findAllCities() {
        return client.get()
                .uri("/apis/cities")
                .retrieve()
                .bodyToFlux(CityServiceResponse.class)
                .log();
    }
}
