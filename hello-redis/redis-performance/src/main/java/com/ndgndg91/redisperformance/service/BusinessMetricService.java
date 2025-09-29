package com.ndgndg91.redisperformance.service;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RScoredSortedSetReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.LongCodec;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessMetricService {

    private final RedissonReactiveClient client;


    public Mono<Map<BigInteger, Double>> top3Products() {
        String format = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
        RScoredSortedSetReactive<BigInteger> set = client.getScoredSortedSet("product:visit:" + format, LongCodec.INSTANCE);

        return set.entryRangeReversed(0, 2)
                .map(listSe -> listSe.stream().collect(Collectors.toMap(
                        ScoredEntry::getValue,
                        ScoredEntry::getScore,
                        (a,b) -> a,
                        LinkedHashMap::new
                )));
    }
}
