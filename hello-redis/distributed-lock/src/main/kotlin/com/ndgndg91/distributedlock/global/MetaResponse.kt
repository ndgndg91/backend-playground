package com.ndgndg91.distributedlock.global

import java.math.BigInteger
import java.time.LocalDateTime
import java.time.ZoneOffset

data class MetaResponse(
    val timestamp: BigInteger = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toBigInteger()
)