package com.ndgndg91.pointapi.controller.dto.request

import com.ndgndg91.pointapi.service.dto.CreatePointPoolCommand

data class RegisterPointPoolRequest(
    val id: Long,
    val accountId: Long,
    val portfolioId: Int,
    val startDatetime: Long,
    val endDatetime: Long,
    val pointValidDays: Int,
    val budget: Long
) {
    fun toCommand(): CreatePointPoolCommand {
        return CreatePointPoolCommand(
            id = id,
            accountId = accountId,
            portfolioId = portfolioId,
            startDatetime = startDatetime,
            endDatetime = endDatetime,
            pointValidDays = pointValidDays,
            budget = budget
        )
    }
}