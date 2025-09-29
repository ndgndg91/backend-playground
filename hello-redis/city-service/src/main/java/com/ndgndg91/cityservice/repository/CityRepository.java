package com.ndgndg91.cityservice.repository;

import com.ndgndg91.cityservice.domain.City;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CityRepository {

    private static final Map<BigInteger, City> source = new ConcurrentHashMap<>();

    static {
        source.put(BigInteger.ONE, new City(BigInteger.ONE, "서울", "Seoul", true));
        source.put(BigInteger.TWO, new City(BigInteger.TWO, "대전", "Daejeon", false));
        source.put(BigInteger.valueOf(3), new City(BigInteger.valueOf(3), "대구", "Dae-gu", false));
        source.put(BigInteger.valueOf(4), new City(BigInteger.valueOf(4), "부산", "Busan", false));
        source.put(BigInteger.valueOf(5), new City(BigInteger.valueOf(5), "울산", "Ulsan", false));
        source.put(BigInteger.valueOf(6), new City(BigInteger.valueOf(6), "광주", "Gwangju", false));
        source.put(BigInteger.valueOf(7), new City(BigInteger.valueOf(7), "인천", "Incheon", false));
        source.put(BigInteger.valueOf(8), new City(BigInteger.valueOf(8), "제주", "Jeju", false));
        source.put(BigInteger.valueOf(9), new City(BigInteger.valueOf(9), "용인", "Yongin", false));
        source.put(BigInteger.TEN, new City(BigInteger.TEN, "수원", "Suwon", false));
    }

    public City findById(final BigInteger id) {
        return source.values().stream().filter(c -> c.id().equals(id)).findFirst().orElseThrow();
    }

    public List<City> findAll() {
        return source.values().stream().toList();
    }
}
