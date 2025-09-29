package com.ndgndg91.cityservice.controller.response;

import com.ndgndg91.cityservice.domain.City;

import java.math.BigInteger;

public record CityResponse(
        BigInteger id,
        String krName,
        String engName,
        boolean isPrime
) {
    public static CityResponse from(City city) {
        return new CityResponse(
                city.id(),
                city.krName(),
                city.engName(),
                city.isPrime()
        );
    }
}
