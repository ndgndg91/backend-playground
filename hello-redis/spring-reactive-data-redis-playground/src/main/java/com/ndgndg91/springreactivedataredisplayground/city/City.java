package com.ndgndg91.springreactivedataredisplayground.city;

import java.math.BigInteger;

public record City(
        BigInteger id,
        String krName,
        String engName,
        boolean isPrime
) {
}
