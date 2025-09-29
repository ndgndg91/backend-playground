package com.ndgndg91.distributedlock.domain.order.repository

import com.ndgndg91.distributedlock.domain.order.entity.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<Order, Long>