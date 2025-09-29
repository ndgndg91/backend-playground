package com.ndgndg91.redisson.test.config.dto;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    private String id;
    private String city;
    private double latitude;
    private double longitude;
    private String name;
    private String zip;
}
