package com.ndgndg91.pointapi.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

/**
 * 포인트 지급 시 사용되는 meta 테이블 엔티티
 */
@Entity
@Table(name = "point_pool")
class PointPool(

    /**
     * 풀 ID
     */
    @Id
    @Column(name = "pool_id")
    var poolId: Long,

    /**
     * 매핑된 내부 업무용 계정 ID
     */
    @Column(name = "account_id", nullable = false)
    val accountId: Long,

    @Column(name = "portfolio_id", nullable = false)
    val portfolioId: Int,

    /**
     * 포인트 지급 가능 시작일시
     */
    @Column(name = "start_datetime", nullable = false)
    var startDatetime: LocalDateTime = LocalDateTime.now(),

    /**
     * 포인트 지급 가능 종료일시
     */
    @Column(name = "end_datetime", nullable = false)
    var endDatetime: LocalDateTime = LocalDateTime.now(),

    /**
     * 포인트 소멸 기한 (일 단위)
     */
    @Column(name = "point_valid_days", nullable = false)
    var pointValidDays: Int,

    /**
     * 상태 (1:진행 중, 2:종료)
     * PointPoolStatusConverter를 통해 DB의 TINYINT(1) 값과 매핑됩니다.
     */
    @Column(name = "status", nullable = false)
    @Convert(converter = PointPoolStatusConverter::class)
    var status: PointPoolStatus = PointPoolStatus.ACTIVE,

    /**
     * 최종 수정 일시
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null,

    /**
     * 생성 일시
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime? = null
)
