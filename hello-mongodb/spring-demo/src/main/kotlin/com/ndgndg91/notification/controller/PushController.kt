package com.ndgndg91.notification.controller

import com.ndgndg91.notification.controller.dto.request.SendBroadCastRequest
import com.ndgndg91.notification.global.dto.response.ApiResponse
import com.ndgndg91.notification.service.BroadCastService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PushController(
    private val service: BroadCastService
) {

    @PostMapping("/apis/notification/mobile-push")
    fun sendBroadCast(
        @RequestBody body: SendBroadCastRequest
    ): ResponseEntity<ApiResponse<Unit>> {
        service.send(body.title, body.body, body.action)
        return ResponseEntity.accepted().body(ApiResponse(Unit))
    }
}