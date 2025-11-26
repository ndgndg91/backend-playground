package com.ndgndg91.project.global.logging

import org.springframework.http.HttpHeaders

data class ResponseLog(
    val headers: HttpHeaders,
    val statusCode: String,
    val body: String?
)