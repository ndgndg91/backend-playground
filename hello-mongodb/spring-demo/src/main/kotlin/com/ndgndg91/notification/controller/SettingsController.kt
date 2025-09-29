package com.ndgndg91.notification.controller

import com.ndgndg91.notification.controller.dto.request.CreateSettingsRequest
import com.ndgndg91.notification.controller.dto.request.UpdateSettingsRequest
import com.ndgndg91.notification.controller.dto.response.SettingsResponse
import com.ndgndg91.notification.global.dto.response.ApiResponse
import com.ndgndg91.notification.service.SettingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class SettingsController(
    private val service: SettingService
){
    @GetMapping("/apis/notification/settings")
    fun findSettings(
        @RequestHeader accountId: Long
    ): ResponseEntity<ApiResponse<SettingsResponse>> {
        return ResponseEntity.ok(
            ApiResponse(SettingsResponse(service.findByAccountId(accountId)))
        )
    }

    @PostMapping("/apis/notification/settings")
    fun createSettings(
        @RequestBody body: CreateSettingsRequest
    ): ResponseEntity<ApiResponse<Unit>> {
        service.save(body)
        return ResponseEntity.created(URI.create("/apis/notification/settings"))
            .build()
    }

    @PutMapping("/apis/notification/settings")
    fun updateSettingS(
        @RequestBody body: UpdateSettingsRequest
    ): ResponseEntity<ApiResponse<Unit>> {
        service.update(body)
        return ResponseEntity.noContent()
            .build()
    }
}