package com.ndgndg91.springreactivedataredisplayground.city;

import java.util.List;

public record CityServiceResponse(
    List<City> data
) {
}
