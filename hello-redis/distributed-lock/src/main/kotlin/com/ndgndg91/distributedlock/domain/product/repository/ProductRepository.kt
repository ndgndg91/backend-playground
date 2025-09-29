package com.ndgndg91.distributedlock.domain.product.repository

import com.ndgndg91.distributedlock.domain.product.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, Long>