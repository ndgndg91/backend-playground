package com.ndgndg91.distributedlock.order.controller.dto.request

import java.math.BigDecimal
import java.math.BigInteger

data class PlaceOrderItemRequest(
    val productId: BigInteger,
    val title: String,
    val quantity: BigInteger,
    val price: BigDecimal
)