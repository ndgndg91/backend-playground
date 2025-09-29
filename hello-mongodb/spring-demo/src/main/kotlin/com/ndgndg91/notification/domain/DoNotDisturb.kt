package com.ndgndg91.notification.domain

data class DoNotDisturb(
    val enabled: Boolean,
    val startTime: String,
    val endTime: String
)