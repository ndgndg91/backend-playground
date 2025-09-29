package com.ndgndg91.notification.global.dto.response

data class ApiResponse<T>(
    val data: T,
    val metadata: ApiMetadataResponse = ApiMetadataResponse()
)
