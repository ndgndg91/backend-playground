package com.ndgndg91.notification.global.dto.response

import java.util.UUID

data class ApiMetadataResponse(
    val code: Int = ApiResponseCode.SUCCESS.code,
    val timestamp: Long = System.currentTimeMillis(),
    val traceId: String = UUID.randomUUID().toString()
)