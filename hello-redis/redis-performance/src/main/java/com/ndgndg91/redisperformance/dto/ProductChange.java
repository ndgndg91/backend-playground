package com.ndgndg91.redisperformance.dto;

import java.math.BigDecimal;

public record ProductChange(
        String description,
        BigDecimal price
) {
}
