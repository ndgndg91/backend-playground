package com.ndgndg91.pointapi.controller.dto.request

import com.ndgndg91.pointapi.service.dto.CreatePointPoolCommand

data class RegisterPointPoolRequest(
    val id: Int,
    val accountId: Int,
    val startDatetime: Long,
    val endDatetime: Long,
    val pointValidDays: Int,
    val budget: Int
) {
    fun toCommand(): CreatePointPoolCommand {
        return CreatePointPoolCommand(
            id = id,
            accountId = accountId,
            startDatetime = startDatetime,
            endDatetime = endDatetime,
            pointValidDays = pointValidDays,
            budget = budget
        )
    }
}