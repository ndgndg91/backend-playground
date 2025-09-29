package com.ndgndg91.distributedlock.global

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RuntimeExceptionAdvisor {

    @ExceptionHandler(IllegalStateException::class)
    fun illegalStateException(e: IllegalStateException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity.internalServerError()
            .body(ApiErrorResponse(e.message!!))
    }
}