package com.ndgndg91.pointapi.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "point_transaction")
class PointTransaction(
    @Id
    @Column(name = "transaction_id")
    var transactionId: Long,

    @Column(name = "account_id")
    val accountId: Long,

    @Column(name = "pool_id")
    val poolId: Long,

    @Column(name = "transaction_type")
    @Convert(converter = TransactionTypeConverter::class)
    val transactionType: TransactionType,

    @Column(name = "amount")
    val amount: Long,

    @Column(name = "source_transaction_id")
    val sourceTransactionId: Long? = null,

    @Column(name = "exchange_id")
    val exchangeId: Long? = null,

    @Column(name = "request_id")
    val requestId: String,

    @Column(name = "reason")
    val reason: String,

    @Column(name = "expires_at")
    val expiresAt: LocalDateTime? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime
)
