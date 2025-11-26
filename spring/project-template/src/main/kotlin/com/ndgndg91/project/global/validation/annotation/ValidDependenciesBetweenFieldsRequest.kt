package com.ndgndg91.project.global.validation.annotation

import com.ndgndg91.project.global.validation.validator.DependenciesBetweenFieldsValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DependenciesBetweenFieldsValidator::class])
@MustBeDocumented
annotation class ValidDependenciesBetweenFieldsRequest(
    val message: String = "41",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)