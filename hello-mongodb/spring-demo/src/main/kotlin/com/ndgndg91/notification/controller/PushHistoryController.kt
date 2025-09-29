package com.ndgndg91.notification.controller

import com.ndgndg91.notification.controller.dto.request.CreatePushHistoryRequest
import com.ndgndg91.notification.global.dto.response.ApiResponse
import com.ndgndg91.notification.service.PushHistoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class PushHistoryController(
    private val service: PushHistoryService
) {

    @PostMapping("/apis/notification/push-history")
    fun createHistory(@RequestBody body: CreatePushHistoryRequest): ResponseEntity<ApiResponse<*>> {
        return ResponseEntity.created(URI.create("/apis/notification/push-history/${service.create(body).id}"))
            .build()
    }

    @GetMapping("/apis/notification/push-history/{id}")
    fun findById(
        @PathVariable("id") id: String
    ): ResponseEntity<ApiResponse<*>> {
        return ResponseEntity.ok(ApiResponse(service.findById(id)))
    }
}