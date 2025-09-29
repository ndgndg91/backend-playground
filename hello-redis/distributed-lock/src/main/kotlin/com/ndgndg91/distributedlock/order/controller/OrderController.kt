package com.ndgndg91.distributedlock.order.controller

import com.ndgndg91.distributedlock.global.ApiResponse
import com.ndgndg91.distributedlock.order.controller.dto.request.PlaceOrderRequest
import com.ndgndg91.distributedlock.order.controller.dto.response.PlaceOrderResponse
import com.ndgndg91.distributedlock.order.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping("/api/orders")
    fun placeOrder(@RequestBody request: PlaceOrderRequest): ResponseEntity<ApiResponse<PlaceOrderResponse>> {
        return ResponseEntity.ok(ApiResponse(PlaceOrderResponse(orderService.placeOrder(request.items).toBigInteger())))
    }
}