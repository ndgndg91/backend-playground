package com.ndgndg91.springreactivedataredisplayground.geo.controller;

import com.ndgndg91.springreactivedataredisplayground.geo.dto.Restaurant;
import com.ndgndg91.springreactivedataredisplayground.geo.service.RestaurantLocatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantLocatorService service;

    @GetMapping("/apis/restaurants/locations")
    public Flux<Restaurant> findRestaurants(
            @RequestParam final String zipCode
    ) {
        return service.findRestaurantsByZipCode(zipCode);
    }
}
