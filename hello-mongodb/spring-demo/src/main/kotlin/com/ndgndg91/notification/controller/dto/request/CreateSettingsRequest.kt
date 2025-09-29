package com.ndgndg91.notification.controller.dto.request

import com.ndgndg91.notification.domain.*

data class CreateSettingsRequest(
    val accountId: Long,
    val orderExecution: Boolean,
    val stopOrderTrigger: Boolean,
    val fiatDepositWithdraw: Boolean,
    val cryptoDepositWithdraw: Boolean,
    val priceChange: Boolean,
    val highLowAlert: Boolean,
    val targetPriceAlert: Boolean,
    val eventAlert: Boolean,
    val communityAlert: Boolean,
    val doNotDisturb: Boolean,
    val doNotDisturbStart: String,
    val doNotDisturbEnd: String
) {
    
    fun toSettings(): Settings {
        return Settings(
            accountId = accountId,
            general = GeneralSettings(
                orderExecution = orderExecution,
                stopOrderTrigger = stopOrderTrigger,
                fiatDepositWithdraw = fiatDepositWithdraw,
                cryptoDepositWithdraw = cryptoDepositWithdraw
            ),
            market = MarketSettings(
                priceChange = priceChange,
                highLowAlert = highLowAlert,
                targetPriceAlert = targetPriceAlert
            ),
            events = EventSettings(
                listingNotice = true,
                eventAlert = eventAlert,
                communityAlert = communityAlert
            ),
            doNotDisturb = DoNotDisturb(
                enabled = doNotDisturb,
                startTime = doNotDisturbStart,
                endTime = doNotDisturbEnd
            )
        )
    }
}
