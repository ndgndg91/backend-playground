package com.ndgndg91.pointapi.global.exception

import com.ndgndg91.pointapi.global.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
@ResponseError(ErrorCode.CLOSED_POOL_STATUS)
class ClosedPoolStatusException(e: RuntimeException): ServiceException(e)