package com.ndgndg91.cityservice.service;

import com.ndgndg91.cityservice.domain.City;
import com.ndgndg91.cityservice.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository repository;

    public City findById(final BigInteger id) {
        return repository.findById(id);
    }

    public List<City> findAll() {
        return repository.findAll();
    }
}
