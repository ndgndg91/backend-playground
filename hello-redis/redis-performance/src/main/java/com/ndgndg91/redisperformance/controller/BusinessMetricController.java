package com.ndgndg91.redisperformance.controller;

import com.ndgndg91.redisperformance.service.BusinessMetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.math.BigInteger;
import java.time.Duration;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BusinessMetricController {

    private final BusinessMetricService service;

    @GetMapping(value = "/apis/products/metrics", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Map<BigInteger, Double>> findTop3() {
        return service.top3Products()
                .repeatWhen(l -> Flux.interval(Duration.ofSeconds(3)));
    }


}
