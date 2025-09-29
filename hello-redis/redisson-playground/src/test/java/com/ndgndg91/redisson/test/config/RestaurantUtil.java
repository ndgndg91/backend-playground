package com.ndgndg91.redisson.test.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndgndg91.redisson.test.config.dto.Restaurant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class RestaurantUtil {

    public static List<Restaurant> getRestaurants() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream stream = RestaurantUtil.class.getClassLoader().getResourceAsStream("restaurant.json");
        try {
            return mapper.readValue(stream, new TypeReference<>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
