package com.ndgndg91.pointapi.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PointPoolSummaryRepository: JpaRepository<PointPoolSummary, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE PointPoolSummary p
        SET p.totalGrantedPoints = p.totalGrantedPoints + :pointsToGrant,
            p.updatedAt = CURRENT_TIMESTAMP
        WHERE p.poolId = :poolId
        AND p.totalBudget >= (p.totalGrantedPoints + :pointsToGrant)
    """)
    fun grantPointsIfSufficient(
        @Param("poolId") poolId: Long,
        @Param("pointsToGrant") pointsToGrant: Long
    ): Int
}