package com.ndgndg91.pointapi.service

import com.ndgndg91.pointapi.entity.PointGrantBalanceRepository
import com.ndgndg91.pointapi.entity.PointPoolRepository
import com.ndgndg91.pointapi.entity.PointPoolStatus
import com.ndgndg91.pointapi.entity.PointPoolSummaryRepository
import com.ndgndg91.pointapi.entity.PointTransactionRepository
import com.ndgndg91.pointapi.global.exception.ClosedPoolStatusException
import com.ndgndg91.pointapi.global.exception.NoFoundPointPoolException
import com.ndgndg91.pointapi.global.exception.NoGrantPeriodException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PointGrantService(
    private val poolRepository: PointPoolRepository,
    private val poolSummaryRepository: PointPoolSummaryRepository,
    private val transactionRepository: PointTransactionRepository,
    private val grantBalanceRepository: PointGrantBalanceRepository
) {

    @Transactional(rollbackFor = [Exception::class])
    fun grant(poolId: Long, accountId: Long, amount: Long) {
        val pool = poolRepository.findByIdOrNull(poolId)
            ?: throw NoFoundPointPoolException(IllegalArgumentException("Point pool with ID $poolId not found"))

        require(pool.status == PointPoolStatus.ACTIVE) {
            throw ClosedPoolStatusException(IllegalArgumentException("Point pool with ID $poolId closed."))
        }
        val now = LocalDateTime.now()
        require(pool.startDatetime <= now && now <= pool.endDatetime) {
            throw NoGrantPeriodException(IllegalArgumentException("${pool.startDatetime} ~ ${pool.endDatetime}"))
        }


        poolSummaryRepository.grantPointsIfSufficient(poolId, amount)

        TODO()
    }
}