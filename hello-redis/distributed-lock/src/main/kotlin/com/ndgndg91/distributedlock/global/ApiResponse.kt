package com.ndgndg91.distributedlock.global

data class ApiResponse<T>(
    val data: T,
    val meta: MetaResponse = MetaResponse()
)