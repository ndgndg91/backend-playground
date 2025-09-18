package com.ndgndg91.pointapi.entity

import org.springframework.data.jpa.repository.JpaRepository

interface PointPoolRepository: JpaRepository<PointPool, Long> {
}