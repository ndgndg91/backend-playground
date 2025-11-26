package com.ndgndg91.project.global.logging

import org.springframework.http.HttpHeaders


data class RequestLog(
    val headers: HttpHeaders,
    val url: String,
    val method: String,
    val ip: String,
    val body: String?
)