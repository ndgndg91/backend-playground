package com.ndgndg91.project.global.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class MetaResponse(
    val responseCode: Int = 0,
    val timestamp: Long = Instant.now().epochSecond,
    @field:JsonInclude(JsonInclude.Include.NON_NULL) val requestId: String? = UUID.randomUUID().toString()
)