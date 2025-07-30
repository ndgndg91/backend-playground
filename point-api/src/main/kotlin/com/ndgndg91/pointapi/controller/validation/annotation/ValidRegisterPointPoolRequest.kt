package com.ndgndg91.pointapi.controller.validation.annotation

import com.ndgndg91.pointapi.controller.validation.validator.RegisterPointPoolValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [RegisterPointPoolValidator::class])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidRegisterPointPoolRequest(
    val message: String = "시작일은 종료일보다 이후일 수 없습니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val start: String,
    val end: String
)