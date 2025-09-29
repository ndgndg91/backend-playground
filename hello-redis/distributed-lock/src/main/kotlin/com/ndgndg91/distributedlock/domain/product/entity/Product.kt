package com.ndgndg91.distributedlock.domain.product.entity

import com.ndgndg91.distributedlock.domain.BaseEntity
import jakarta.persistence.*
import java.math.BigDecimal
import java.math.BigInteger

@Entity
class Product: BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var title: String = ""

    @Column(nullable = false)
    var price: BigDecimal = BigDecimal.ZERO

    @Column(nullable = false)
    var quantity: BigInteger = BigInteger.ZERO
}