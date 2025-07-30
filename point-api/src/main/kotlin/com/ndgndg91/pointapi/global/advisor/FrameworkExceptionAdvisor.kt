package com.ndgndg91.pointapi.global.advisor

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class FrameworkExceptionAdvisor {
    @ExceptionHandler(NoResourceFoundException::class)
    fun noResourceFoundException(): ResponseEntity<ProblemDetail> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ProblemDetail.forStatus(HttpStatus.NOT_FOUND))
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun httpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ProblemDetail> {
        return ResponseEntity
            .badRequest()
            .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.message))
    }
}