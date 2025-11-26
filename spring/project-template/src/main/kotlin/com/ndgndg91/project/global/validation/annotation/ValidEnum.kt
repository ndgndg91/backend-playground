package com.ndgndg91.project.global.validation.annotation

import com.ndgndg91.project.global.validation.validator.EnumValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EnumValidator::class])
@MustBeDocumented
annotation class ValidEnum(
    val message: String = "41",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val enumClass: KClass<out Enum<*>>,
    val nullable: Boolean = false
)