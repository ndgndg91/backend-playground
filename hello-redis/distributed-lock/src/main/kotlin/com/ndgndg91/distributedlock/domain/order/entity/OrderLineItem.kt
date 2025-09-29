package com.ndgndg91.distributedlock.domain.order.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.math.BigInteger

@Entity
data class OrderLineItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var title: String = "",

    @Column
    var price: BigDecimal = BigDecimal.ZERO,

    @Column
    var quantity: BigInteger = BigInteger.ZERO,

    @ManyToOne
    @JoinColumn(name = "order_id")
    var order: Order? = null
)