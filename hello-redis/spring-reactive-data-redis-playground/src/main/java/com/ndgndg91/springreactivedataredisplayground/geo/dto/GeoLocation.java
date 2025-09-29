package com.ndgndg91.springreactivedataredisplayground.geo.dto;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocation {
    private double longitude;
    private double latitude;
}
