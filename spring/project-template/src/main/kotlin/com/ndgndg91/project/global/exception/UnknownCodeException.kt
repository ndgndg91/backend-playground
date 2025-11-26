package com.ndgndg91.project.global.exception

import com.ndgndg91.project.global.annotation.ResponseError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseError(ErrorCode.UNKNOWN_CODE)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class UnknownCodeException(e: RuntimeException?): ServiceException(e)