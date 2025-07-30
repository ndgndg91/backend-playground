package com.ndgndg91.pointapi.global.exception

import com.ndgndg91.pointapi.global.ErrorCode
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class ServiceException(e: Exception?) : RuntimeException(e) {
    fun getErrorCode(): ErrorCode {
        return AnnotationUtils
            .findAnnotation(this.javaClass, ResponseError::class.java)?.errorCode
            ?: ErrorCode.INVALID_PARAM
    }

    fun getResponseStatus(): HttpStatus {
        return AnnotationUtils
            .findAnnotation(this.javaClass, ResponseStatus::class.java)?.value
            ?: HttpStatus.BAD_REQUEST
    }
}