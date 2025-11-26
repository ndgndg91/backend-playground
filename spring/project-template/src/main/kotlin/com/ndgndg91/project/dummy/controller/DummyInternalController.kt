package com.ndgndg91.project.dummy.controller

import com.ndgndg91.project.dummy.service.DummyService
import com.ndgndg91.project.global.dto.SuccessResponse
import com.ndgndg91.project.global.ext.toSuccessResponse
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 서버 내부 호출부
 */
@RestController
class DummyInternalController(
    private val service: DummyService
) {

    @GetMapping("/projects/items")
    fun findDummyItems(
        @RequestParam @Min(0) page: Int,
        @RequestParam @Min(0) @Max(100) pageSize: Int
    ): SuccessResponse<*> {
        return service.findAll(page, pageSize).toSuccessResponse()
    }
}