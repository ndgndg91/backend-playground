package com.ndgndg91.pointapi.global.exception

import com.ndgndg91.pointapi.global.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseError(ErrorCode.INVALID_PARAM)
@ResponseStatus(HttpStatus.BAD_REQUEST)
class AlreadyCreatedPoolException(e: RuntimeException): ServiceException(e)