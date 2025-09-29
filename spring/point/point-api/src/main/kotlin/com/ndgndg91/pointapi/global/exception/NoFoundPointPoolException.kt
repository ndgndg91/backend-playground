package com.ndgndg91.pointapi.global.exception

import com.ndgndg91.pointapi.global.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
@ResponseError(ErrorCode.NO_FOUND_POINT_POOL)
class NoFoundPointPoolException(e: RuntimeException): ServiceException(e)