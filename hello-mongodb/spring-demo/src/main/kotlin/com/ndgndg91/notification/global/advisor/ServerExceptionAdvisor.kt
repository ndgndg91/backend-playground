package com.ndgndg91.notification.global.advisor

import com.ndgndg91.notification.global.dto.response.ApiErrorResponse
import com.ndgndg91.notification.global.dto.response.ApiResponseCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ServerExceptionAdvisor {

    @ExceptionHandler(Exception::class)
    fun exception(e: Exception): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity.internalServerError()
            .body(ApiErrorResponse.error(e.message ?: "unknown error occurred", ApiResponseCode.UNKNOWN_ERROR.code))
    }
}