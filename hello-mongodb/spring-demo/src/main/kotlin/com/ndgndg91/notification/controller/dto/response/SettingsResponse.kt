package com.ndgndg91.notification.controller.dto.response

import com.ndgndg91.notification.domain.Settings

data class SettingsResponse(
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
    constructor(settings: Settings) : this(
        accountId = settings.accountId,
        orderExecution = settings.general.orderExecution,
        stopOrderTrigger = settings.general.stopOrderTrigger,
        fiatDepositWithdraw = settings.general.fiatDepositWithdraw,
        cryptoDepositWithdraw = settings.general.cryptoDepositWithdraw,
        priceChange = settings.market.priceChange,
        highLowAlert = settings.market.highLowAlert,
        targetPriceAlert = settings.market.targetPriceAlert,
        eventAlert = settings.events.eventAlert,
        communityAlert = settings.events.communityAlert,
        doNotDisturb = settings.doNotDisturb.enabled,
        doNotDisturbStart = settings.doNotDisturb.startTime,
        doNotDisturbEnd = settings.doNotDisturb.endTime
    )
}