package com.ndgndg91.pointapi.controller.dto.request

data class RegisterPointPoolRequest(
    val id: Int,
    val accountId: Int,
    val startDatetime: Long,
    val endDatetime: Long,
    val pointValidDays: Int,
    val budget: Int
)