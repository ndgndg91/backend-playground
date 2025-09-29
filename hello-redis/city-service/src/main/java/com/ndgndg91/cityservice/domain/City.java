package com.ndgndg91.cityservice.domain;

import java.math.BigInteger;

public record City(
        BigInteger id,
        String krName,
        String engName,
        boolean isPrime
){

}
