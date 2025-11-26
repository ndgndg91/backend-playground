package com.ndgndg91.project.global.validation.validator

import com.ndgndg91.project.global.exception.ErrorCode
import com.ndgndg91.project.global.validation.annotation.ValidEnumSet
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

class EnumSetValidator: ConstraintValidator<ValidEnumSet, List<String>> {
    private lateinit var enumValues: List<String>
    private lateinit var enumClass: KClass<out Enum<*>>

    override fun initialize(constraintAnnotation: ValidEnumSet) {
        enumValues = constraintAnnotation.enumClass.java.enumConstants.map { it.name }
        enumClass = constraintAnnotation.enumClass
    }

    override fun isValid(value: List<String>, context: ConstraintValidatorContext): Boolean {
        val invalidElements = value.filter { !enumValues.contains(it.uppercase()) }
        return if (invalidElements.isNotEmpty()) {
            addConstraint(context)
        } else {
            true
        }
    }

    private fun addConstraint(context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()
        when(enumClass) {
            /**
             * 추가
             */
            else -> {
                context
                    .buildConstraintViolationWithTemplate(ErrorCode.INVALID_PARAMETER.code.toString())
                    .addConstraintViolation()
            }
        }

        return false
    }
}
