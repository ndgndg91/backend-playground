package com.ndgndg91.project.global.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class SuccessResponse<T>(
        val body: T,
        val meta: MetaResponse = MetaResponse()
)