package com.ndgndg91.notification.domain

data class GeneralSettings(
    val orderExecution: Boolean,
    val stopOrderTrigger: Boolean,
    val fiatDepositWithdraw: Boolean,
    val cryptoDepositWithdraw: Boolean
)