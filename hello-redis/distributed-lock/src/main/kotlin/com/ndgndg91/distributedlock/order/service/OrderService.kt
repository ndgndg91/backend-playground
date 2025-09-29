package com.ndgndg91.distributedlock.order.service

import com.ndgndg91.distributedlock.domain.lock.LockRepository
import com.ndgndg91.distributedlock.domain.order.entity.Order
import com.ndgndg91.distributedlock.domain.order.entity.OrderLineItem
import com.ndgndg91.distributedlock.domain.order.repository.OrderRepository
import com.ndgndg91.distributedlock.domain.product.repository.ProductRepository
import com.ndgndg91.distributedlock.order.controller.dto.request.PlaceOrderItemRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val lockRepository: LockRepository
) {

    @Transactional
    fun placeOrder(items: List<PlaceOrderItemRequest>): Long {
        val products = productRepository.findAllById(items.map { it.productId.toLong() })
        products.forEach {
            val acquireLock = lockRepository.acquireLock("PRODUCT:QUANTITY:${it.id}", "DISTRIBUTED-LOCK", 1)
            if (!acquireLock) throw IllegalStateException("Cannot acquire Lock ${it.id}")
        }

        val productMap = products.associateBy { it.id }
        items.forEach {
            val product = productMap[it.productId.toLong()]
            if (product != null) {
                product.quantity -= it.quantity
            }
        }

        val order = Order("테스트 주문").run {
            this.items.addAll(items.map {
                OrderLineItem(
                    title = it.title,
                    price = it.price,
                    quantity = it.quantity,
                    order = this
                )
            })
            this
        }

        products.forEach {
            lockRepository.releaseLock("PRODUCT:QUANTITY:${it.id}", "DISTRIBUTED-LOCK")
        }

        return orderRepository.save(order).id!!
    }
}