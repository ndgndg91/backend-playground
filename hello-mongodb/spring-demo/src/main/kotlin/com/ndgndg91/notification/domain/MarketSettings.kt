package com.ndgndg91.notification.domain

data class MarketSettings(
    val priceChange: Boolean,
    val highLowAlert: Boolean,
    val targetPriceAlert: Boolean
)