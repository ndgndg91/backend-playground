package com.ndgndg91.project.global.logging


data class AccessLog(
    val errorMessage: String?,
    val request: RequestLog,
    val response: ResponseLog,
) {
    companion object {
        fun ok(request: RequestLog, response: ResponseLog): AccessLog = AccessLog(
            errorMessage = null,
            request = request,
            response = response,
        )

        fun error(errorMessage: String?, request: RequestLog, response: ResponseLog): AccessLog = AccessLog(
            errorMessage = errorMessage,
            request = request,
            response = response,
        )
    }

    fun toMap(): Map<String, String> {
        val map = mutableMapOf(
            "error_message" to (errorMessage ?: ""),
            "url" to request.url,
            "method" to request.method,
            "ip" to request.ip,
        )

        request.headers.forEach { (key, values) ->
            map["header.RAW_HEADER.$key"] = values.joinToString(",")
        }
        map["body"] = (request.body ?: "")

        response.headers.forEach { (key, values) ->
            map["header.response.$key"] = values.joinToString(",")
        }
        map["status_code"] = response.statusCode
        map["response"] = (response.body ?: "")
        return map
    }
}