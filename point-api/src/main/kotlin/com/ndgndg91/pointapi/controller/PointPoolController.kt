package com.ndgndg91.pointapi.controller

import com.ndgndg91.pointapi.controller.dto.request.RegisterPointPoolRequest
import com.ndgndg91.pointapi.service.PointPoolService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PointPoolController(
    private val service: PointPoolService
) {

    @PostMapping("/api/point-pools")
    fun createPointPool(@Valid @RequestBody body: RegisterPointPoolRequest): ResponseEntity<Unit> {
        service.creatPointPool(body.toCommand())
        return ResponseEntity.ok().build()
    }
}