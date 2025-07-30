package com.ndgndg91.pointapi.service.dto

data class CreatePointPoolCommand(
    val id: Int,
    val accountId: Int,
    val startDatetime: Long,
    val endDatetime: Long,
    val pointValidDays: Int,
    val budget: Int
)