package com.ndgndg91.project.global.advisor

import com.ndgndg91.project.global.dto.ErrorResponse
import com.ndgndg91.project.global.dto.MetaResponse
import com.ndgndg91.project.global.exception.ErrorCode
import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException

@RestControllerAdvice
class RequestValidationAdvisor {
    private val logger = LoggerFactory.getLogger(RequestValidationAdvisor::class.java)

    @ExceptionHandler(HandlerMethodValidationException::class)
    fun handleValidationException(e: HandlerMethodValidationException): ResponseEntity<ErrorResponse> {
        logger.error("handling validation error", e)
        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse(
                    error = e.message,
                    meta = MetaResponse(responseCode = ErrorCode.INVALID_PARAMETER.code)
                )
            )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val error = e.bindingResult.allErrors.firstOrNull()
        var errorCode: ErrorCode = ErrorCode.INVALID_PARAMETER
        var errorMessage = ""
        if (error != null) {
            when(error) {
                is FieldError -> {
                    val fieldName = error.field
                    val errorCodeString = error.defaultMessage
                    if (errorCodeString != null) {
                        errorCode = ErrorCode.findByCode(errorCodeString.toInt())
                        errorMessage = "$fieldName is invalid."
                    }
                }
                else -> {
                    val objectName = error.objectName
                    val errorCodeString = error.defaultMessage
                    if (errorCodeString != null) {
                        errorCode = ErrorCode.findByCode(errorCodeString.toInt())
                        errorMessage = "$objectName is invalid."
                    }
                }
            }
        }

        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse(
                    error = mapOf("message" to errorMessage),
                    meta = MetaResponse(responseCode = errorCode.code)
                )
            )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolationException(e: ConstraintViolationException): ResponseEntity<ErrorResponse> {
        e.constraintViolations.forEach {
            logger.info("cv : {}", it)
        }
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse(meta = MetaResponse(responseCode = ErrorCode.INVALID_PARAMETER.code)))
    }

    @Suppress("UnusedParameter")
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableExceptionHandler(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse(meta = MetaResponse(responseCode = ErrorCode.INVALID_PARAMETER.code)))
    }
}