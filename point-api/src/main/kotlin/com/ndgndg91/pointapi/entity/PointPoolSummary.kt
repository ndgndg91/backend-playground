package com.ndgndg91.pointapi.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "point_pool_summary")
class PointPoolSummary(
    @Id
    @Column(name = "pool_id")
    val poolId: Long,
    @Column(name = "total_budget")
    var totalBudget: Long,
    @Column(name = "total_granted_points")
    var totalGrantedPoints: Long,
    @Column(name = "total_krw_exchanged_points")
    var totalKrwExchangedPoints: Long,
    @Column(name = "total_commission_amount")
    var totalCommissionAmount: Long,
    @Column(name = "total_coupon_exchanged_points")
    var totalCouponExchangedPoints: Long,
    @Column(name = "total_expired_points")
    var totalExpiredPoints: Long,
    @Column(name = "total_reclaimed_points")
    var totalReclaimedPoints: Long,
    @Column(name = "last_aggregated_date")
    val lastAggregatedDate: LocalDateTime?,
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime,
    @Column(name = "created_at")
    val createdAt: LocalDateTime
)