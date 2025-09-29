package com.ndgndg91.springreactivedataredisplayground.city.controller;

import com.ndgndg91.springreactivedataredisplayground.city.City;
import com.ndgndg91.springreactivedataredisplayground.city.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityService service;

    @GetMapping("/apis/cities/{id}")
    public Mono<City> findById(
            @PathVariable BigInteger id
    ) {
        return service.findCityById(id);
    }

    @GetMapping("/apis/citites")
    public Flux<City> findAll() {
        return service.findAll();
    }
}
