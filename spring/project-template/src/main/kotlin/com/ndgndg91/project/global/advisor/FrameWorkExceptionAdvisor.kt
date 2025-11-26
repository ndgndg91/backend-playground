package com.ndgndg91.project.global.advisor

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.ndgndg91.project.global.dto.ErrorResponse
import com.ndgndg91.project.global.dto.MetaResponse
import com.ndgndg91.project.global.exception.ErrorCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.server.MethodNotAllowedException
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class FrameWorkExceptionAdvisor {
    @Suppress("UnusedParameter")
    @ExceptionHandler(MethodNotAllowedException::class)
    fun methodNotAllowed(e: MethodNotAllowedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse(meta = MetaResponse(responseCode = ErrorCode.INVALID_PARAMETER.code)))
    }
    @Suppress("UnusedParameter")
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun methodArgumentTypeMisMatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse(meta = MetaResponse(responseCode = ErrorCode.INVALID_PARAMETER.code)))
    }
    @Suppress("UnusedParameter")
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun missingServletRequestParameterException(e: MissingServletRequestParameterException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse(meta = MetaResponse(responseCode = ErrorCode.INVALID_PARAMETER.code)))
    }
    @Suppress("UnusedParameter")
    @ExceptionHandler(MismatchedInputException::class)
    fun mismatchedInputException(e: MismatchedInputException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse(meta = MetaResponse(responseCode = ErrorCode.INVALID_PARAMETER.code)))
    }
    @Suppress("UnusedParameter")
    @ExceptionHandler(NoResourceFoundException::class)
    fun noResourceFoundException(e: NoResourceFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.notFound()
            .build()
    }
}


