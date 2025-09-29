package com.ndgndg91.notification.global.dto.response

data class ApiErrorResponse(
    val error: Any,
    val metadata: ApiMetadataResponse
) {
    companion object {
        fun error(message: String, code: Int): ApiErrorResponse {
            return ApiErrorResponse(
                error = message,
                metadata = ApiMetadataResponse(
                    code = code
                )
            )
        }
    }
}