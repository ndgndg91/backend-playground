package com.ndgndg91.project.global.ext

import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset

internal const val ISO_8061_TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
var MANUAL_CLOCK: Clock? = null
const val NINE = 9
val KST_ZONE_OFFSET: ZoneOffset = ZoneOffset.ofHours(NINE)

fun now(zoneOffset: ZoneOffset = ZoneOffset.UTC): LocalDateTime {
    return if (MANUAL_CLOCK == null) LocalDateTime.now(zoneOffset) else LocalDateTime.now(MANUAL_CLOCK)
}