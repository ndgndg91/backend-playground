package com.ndgndg91.pointapi.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "point_grant_balance")
class PointGrantBalance(
    @Id
    @Column(name = "grant_transaction_id")
    var grantTransactionId: Long,

    @Column(name = "remaining_amount")
    var remainingAmount: Long,

    @Column(name = "expires_at")
    val expiresAt: LocalDateTime,

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,
)