package com.ndgndg91.distributedlock.global

data class ApiErrorResponse(
    val message: String,
    val meta: MetaResponse = MetaResponse()
)
