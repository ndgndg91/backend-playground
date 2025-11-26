package com.ndgndg91.project.global.validation.annotation

import com.ndgndg91.project.global.validation.validator.EnumSetValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EnumSetValidator::class])
@MustBeDocumented
annotation class ValidEnumSet(
    val message: String = "41",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val enumClass: KClass<out Enum<*>>
)
