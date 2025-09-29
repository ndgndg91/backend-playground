package com.ndgndg91.notification.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "settings")
data class Settings(
    @Id
    val accountId: Long,
    val general: GeneralSettings,
    val market: MarketSettings,
    val events: EventSettings,
    val doNotDisturb: DoNotDisturb
)

