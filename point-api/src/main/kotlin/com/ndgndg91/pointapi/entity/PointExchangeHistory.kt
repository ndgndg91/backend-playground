package com.ndgndg91.pointapi.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

// TODO
@Entity
@Table(name = "point_exchange_history")
class PointExchangeHistory(
    @Id
    val id: Long,
    val accountId: Long,
    val exchangeAmount: Long,
    val receivedKrwAmount: Long,
    val commissionAmount: Long,
    val exchangeStatus: String,
    val requestAt: LocalDateTime,
    val createdAt: LocalDateTime,
)