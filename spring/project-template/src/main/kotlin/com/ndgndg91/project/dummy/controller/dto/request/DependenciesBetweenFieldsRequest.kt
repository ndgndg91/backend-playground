package com.ndgndg91.project.dummy.controller.dto.request

import com.ndgndg91.project.global.validation.annotation.ValidDependenciesBetweenFieldsRequest


@ValidDependenciesBetweenFieldsRequest
data class DependenciesBetweenFieldsRequest(
    val a: Int?,
    val dependOnA: String?
)