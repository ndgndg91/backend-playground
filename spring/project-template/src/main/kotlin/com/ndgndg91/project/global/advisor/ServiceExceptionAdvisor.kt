package com.ndgndg91.project.global.advisor

import com.ndgndg91.project.global.dto.ErrorResponse
import com.ndgndg91.project.global.dto.MetaResponse
import com.ndgndg91.project.global.exception.ErrorCode
import com.ndgndg91.project.global.exception.ServiceException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ServiceExceptionAdvisor {
    private val log = LoggerFactory.getLogger(ServiceExceptionAdvisor::class.java)
    @ExceptionHandler(ServiceException::class)
    fun serviceException(e: ServiceException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(e.getResponseStatus())
            .body(ErrorResponse(meta = MetaResponse(responseCode = e.getErrorCode().code)))
    }

    @ExceptionHandler(Exception::class)
    fun blackHall(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("Exception", e)
        return ResponseEntity.internalServerError()
            .body(ErrorResponse(meta = MetaResponse(responseCode = ErrorCode.UNKNOWN_ERROR.code)))
    }

}