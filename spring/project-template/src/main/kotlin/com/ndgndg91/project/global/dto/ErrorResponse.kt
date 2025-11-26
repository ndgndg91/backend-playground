package com.ndgndg91.project.global.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ErrorResponse(
    val error: Any? = null,
    val meta: MetaResponse = MetaResponse()
)
