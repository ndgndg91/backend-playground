package com.ndgndg91.springcommonbeans

import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

val CLOCK: Clock? = null
val KST_ZONE_ID: ZoneId = ZoneId.of("Asia/Seoul")
val UTC_ZONE_ID: ZoneId = ZoneId.of("UTC")

fun now(zoneId: ZoneId = UTC_ZONE_ID): LocalDateTime {
    return if (CLOCK != null) LocalDateTime.now(CLOCK) else LocalDateTime.now(zoneId)
}

fun nowUTC(): ZonedDateTime {
    return ZonedDateTime.now(UTC_ZONE_ID)
}

fun nowKST(): ZonedDateTime {
    return ZonedDateTime.now(KST_ZONE_ID)
}

fun LocalDate.toZonedDatetime(zoneId: ZoneId = UTC_ZONE_ID): ZonedDateTime {
    return ZonedDateTime.of(this, LocalTime.MIN, zoneId)
}

fun LocalDateTime.toZonedDatetime(zoneId: ZoneId): ZonedDateTime {
    return ZonedDateTime.of(this, zoneId)
}

fun LocalDateTime.toEpochMilli(zoneId: ZoneId = UTC_ZONE_ID): Long {
    return this.toZonedDatetime(zoneId).toInstant().toEpochMilli()
}

fun LocalDateTime.toEpochSecond(zoneId: ZoneId = UTC_ZONE_ID): Long {
    return this.toZonedDatetime(zoneId).toInstant().epochSecond
}