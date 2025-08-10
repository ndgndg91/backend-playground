package com.ndgndg91.pointapi.service

import com.ndgndg91.pointapi.entity.*
import com.ndgndg91.pointapi.global.exception.AlreadyCreatedPoolException
import com.ndgndg91.pointapi.service.dto.CreatePointPoolCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class PointPoolService(
    private val pointPoolRepository: PointPoolRepository,
    private val pointPoolSummaryRepository: PointPoolSummaryRepository
) {
    @Transactional(rollbackFor = [Exception::class])
    fun creatPointPool(command: CreatePointPoolCommand) {
        if (pointPoolRepository.existsById(command.id)) {
            throw AlreadyCreatedPoolException(IllegalArgumentException("A point pool with id ${command.id} already exists"))
        }

        val startDatetime = Instant.ofEpochSecond(command.startDatetime)
            .atZone(ZoneId.of("Asia/Seoul"))
            .toLocalDateTime()
        val endDatetime = Instant.ofEpochSecond(command.endDatetime)
            .atZone(ZoneId.of("Asia/Seoul"))
            .toLocalDateTime()
        val pointPool = PointPool(
            command.id,
            command.accountId,
            command.portfolioId,
            startDatetime,
            endDatetime,
            command.pointValidDays,
            PointPoolStatus.ACTIVE,
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        val pointPoolSummary = PointPoolSummary(
            command.id,
            command.budget,
            0,
            0,
            0,
            0,
            0,
            0,
            null,
            LocalDateTime.now(),
            LocalDateTime.now(),
        )
        pointPoolRepository.save(pointPool)
        pointPoolSummaryRepository.save(pointPoolSummary)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun addBudget() {

    }

    @Transactional(rollbackFor = [Exception::class])
    fun changePoolPeriod() {

    }
}