package com.ndgndg91.pointapi.service.dto

data class CreatePointPoolCommand(
    val id: Long,
    val accountId: Long,
    val portfolioId: Int,
    val startDatetime: Long,
    val endDatetime: Long,
    val pointValidDays: Int,
    val budget: Long
)