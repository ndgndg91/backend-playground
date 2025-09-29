package com.ndgndg91.springreactivedataredisplayground.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final ExternalService client;

    @Cacheable("weather")
    public int getInfo(int zip) {
        return 0;
    }

    @Scheduled(fixedDelay = 10_000)
    public void update() {
        IntStream.rangeClosed(1,5)
                .forEach(client::getWeatherInfo);
    }
}
