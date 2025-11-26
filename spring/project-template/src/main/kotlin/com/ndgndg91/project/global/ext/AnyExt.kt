package com.ndgndg91.project.global.ext

import com.ndgndg91.project.global.dto.MetaResponse
import com.ndgndg91.project.global.dto.SuccessResponse


inline fun <reified T : Any> T.toSuccessResponse(): SuccessResponse<T> {
    return SuccessResponse(this, MetaResponse())
}