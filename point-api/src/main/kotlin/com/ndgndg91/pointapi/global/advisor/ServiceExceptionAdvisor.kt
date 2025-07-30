package com.ndgndg91.pointapi.global.advisor

import com.ndgndg91.pointapi.global.ErrorCode
import com.ndgndg91.pointapi.global.exception.ServiceException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ServiceExceptionAdvisor {
    private val logger = LoggerFactory.getLogger(ServiceExceptionAdvisor::class.java)

    @ExceptionHandler(ServiceException::class)
    fun serviceExceptionHandler(e: ServiceException): ResponseEntity<ProblemDetail> {
        logger.error("ServiceException : ", e)
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.message)
        problemDetail.setProperty("errorCode", e.getErrorCode().code)
        return ResponseEntity.status(e.getResponseStatus()).body(problemDetail)
    }

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(e: Exception): ResponseEntity<ProblemDetail> {
        logger.error("Exception : ", e)
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.message)
        problemDetail.setProperty("errorCode", ErrorCode.UNKNOWN.code)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(problemDetail)
    }
}