package com.ndgndg91.pointapi.global.advisor

import com.ndgndg91.pointapi.global.ErrorCode
import com.ndgndg91.pointapi.global.constant.AUTH_HEADER
import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.security.InvalidParameterException
import kotlin.text.toInt

@RestControllerAdvice
class RequestValidationAdvisor {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolationException(e: ConstraintViolationException): ResponseEntity<ProblemDetail> {
        e.constraintViolations.forEach {
            logger.info("cv : {}", it)
        }
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.message)
        problemDetail.setProperty("errorCode", ErrorCode.INVALID_PARAM.code)
        return ResponseEntity
            .badRequest()
            .body(problemDetail)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<ProblemDetail> {
        val error = e.bindingResult.allErrors.firstOrNull()
        var errorCode: ErrorCode = ErrorCode.INVALID_PARAM
        if (error != null) {
            val errorCodeString = error.defaultMessage
            if (errorCodeString != null) {
                errorCode = ErrorCode.findByCode(errorCodeString.toInt())
            }
        }
        logger.error("MethodArgumentNotValidException : ", e)
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.message)
        problemDetail.setProperty("errorCode", errorCode.code)
        return ResponseEntity
            .badRequest()
            .body(problemDetail)
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun missingServletRequestParameterExceptionHandler(e: MissingServletRequestParameterException): ResponseEntity<ProblemDetail> {
        logger.error("MissingServletRequestParameterException : ", e)
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.message)
        problemDetail.setProperty("errorCode", ErrorCode.INVALID_PARAM.code)
        return ResponseEntity
            .badRequest()
            .body(problemDetail)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableExceptionHandler(e: HttpMessageNotReadableException): ResponseEntity<ProblemDetail> {
        logger.error("HttpMessageNotReadableException : ", e)
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.message)
        problemDetail.setProperty("errorCode", ErrorCode.INVALID_PARAM.code)
        return ResponseEntity
            .badRequest()
            .body(problemDetail)
    }

    @ExceptionHandler(InvalidParameterException::class)
    fun invalidParameterException(e: InvalidParameterException): ResponseEntity<ProblemDetail> {
        logger.error("InvalidParameterException : ", e)
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.message)
        problemDetail.setProperty("errorCode", ErrorCode.INVALID_PARAM.code)
        return ResponseEntity
            .badRequest()
            .body(problemDetail)
    }

    @ExceptionHandler(MissingRequestHeaderException::class)
    fun missingRequestHeadException(e: MissingRequestHeaderException): ResponseEntity<ProblemDetail> {
        logger.error("MissingRequestHeaderException : ", e)
        return if (e.headerName == AUTH_HEADER) {
            val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.message)
            problemDetail.setProperty("errorCode", ErrorCode.INVALID_PARAM.code)
            ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(problemDetail)
        } else {
            val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.message)
            problemDetail.setProperty("errorCode", ErrorCode.INVALID_PARAM.code)
            ResponseEntity
                .badRequest()
                .body(problemDetail)
        }
    }
}