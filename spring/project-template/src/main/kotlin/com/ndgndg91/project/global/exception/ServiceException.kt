package com.ndgndg91.project.global.exception

import com.ndgndg91.project.global.annotation.ResponseError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.jvm.java

open class ServiceException(e: Exception?) : RuntimeException(e) {
    fun getErrorCode(): ErrorCode {
        return this.javaClass.getAnnotation(ResponseError::class.java).errorCode
    }

    fun getResponseStatus(): HttpStatus {
        return this.javaClass.getAnnotation(ResponseStatus::class.java).value
    }
}