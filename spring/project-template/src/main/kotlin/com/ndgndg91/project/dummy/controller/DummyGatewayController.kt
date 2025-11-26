package com.ndgndg91.project.dummy.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import com.ndgndg91.project.dummy.controller.dto.request.CreateDummyRequest
import com.ndgndg91.project.dummy.controller.dto.request.DependenciesBetweenFieldsRequest
import com.ndgndg91.project.dummy.service.DummyService
import com.ndgndg91.project.global.dto.SuccessResponse
import com.ndgndg91.project.global.ext.toSuccessResponse
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * client 호출의 진입점
 */
@Validated
@RestController
class DummyGatewayController(
    private val service: DummyService
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/dummy/items")
    fun findDummyItems(
        @RequestParam @Min(0) page: Int,
        @RequestParam @Min(0) @Max(100) pageSize: Int
    ): SuccessResponse<*> {
        return service.findAll(page, pageSize).toSuccessResponse()
    }

    @GetMapping("/dummy/items/{id}")
    fun findDummyItemById(
        @PathVariable @Min(1) id: Long
    ): SuccessResponse<*> {
        return service.findById(id).toSuccessResponse()
    }

    @PostMapping("/dummy/items")
    fun createDummyItem(
        @RequestBody @Valid body: CreateDummyRequest
    ): SuccessResponse<*> {
        return service.create(body.toCommand()).toSuccessResponse()
    }

    @PutMapping("/dummy/items")
    fun updateDummyItem(
        @RequestBody @Valid body: Any
    ): SuccessResponse<*> {
        return service.update(body).toSuccessResponse()
    }

    @DeleteMapping("/dummy/items")
    fun deleteDummyItem(
        @RequestParam @Min(1) id: Long
    ): SuccessResponse<*> {
        return service.deleteById(id).toSuccessResponse()
    }

    @PostMapping("/dummy/items/depend-on")
    fun exampleDependOn(
        @RequestBody @Valid body: DependenciesBetweenFieldsRequest
    ): SuccessResponse<Unit> {
        logger.info("avoid detekt UnusedParameter : {}", body)
        return Unit.toSuccessResponse()
    }
}