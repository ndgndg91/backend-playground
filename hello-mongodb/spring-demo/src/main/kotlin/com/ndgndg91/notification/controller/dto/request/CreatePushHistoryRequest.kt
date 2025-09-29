package com.ndgndg91.notification.controller.dto.request

data class CreatePushHistoryRequest(
    val accountId: Long,
    val category: String,
    val type: String,
    val title: String,
    val body: String,
    val action: String
)
