package com.ndgndg91.pointapi.controller

import com.ndgndg91.pointapi.controller.dto.request.RegisterPointPoolRequest
import com.ndgndg91.pointapi.entity.PointPoolRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PointPoolController(private val pointPoolRepository: PointPoolRepository) {

    @PostMapping("/api/point-pools")
    fun createPointPool(@RequestBody body: RegisterPointPoolRequest): ResponseEntity<Unit> {
        TODO()
    }
}