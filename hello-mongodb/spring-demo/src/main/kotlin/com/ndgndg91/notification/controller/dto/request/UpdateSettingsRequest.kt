package com.ndgndg91.notification.controller.dto.request

import com.ndgndg91.notification.domain.*

data class UpdateSettingsRequest(
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
            accountId = this.accountId,
            general = GeneralSettings(
                orderExecution = this.orderExecution,
                stopOrderTrigger = this.stopOrderTrigger,
                fiatDepositWithdraw = this.fiatDepositWithdraw,
                cryptoDepositWithdraw = this.cryptoDepositWithdraw
            ),
            market = MarketSettings(
                priceChange = this.priceChange,
                highLowAlert = this.highLowAlert,
                targetPriceAlert = this.targetPriceAlert
            ),
            events = EventSettings(
                listingNotice = true,
                eventAlert = this.eventAlert,
                communityAlert = this.communityAlert
            ),
            doNotDisturb = DoNotDisturb(
                enabled = this.doNotDisturb,
                startTime = this.doNotDisturbStart,
                endTime = this.doNotDisturbEnd
            )
        )
    }
}