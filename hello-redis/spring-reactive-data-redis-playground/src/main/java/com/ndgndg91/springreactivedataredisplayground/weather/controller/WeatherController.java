package com.ndgndg91.springreactivedataredisplayground.weather.controller;

import com.ndgndg91.springreactivedataredisplayground.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService service;

    @GetMapping("/weather/{zip}")
    public Mono<Integer> weather(@PathVariable int zip) {
        return Mono.fromSupplier(() -> service.getInfo(zip));
    }
}
